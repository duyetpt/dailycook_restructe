package com.vn.dailycookapp.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.notification.NotificationWorker;
import com.vn.dailycookapp.security.session.SessionManager;

/**
 * http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/examples/
 * embedded/src/main/java/org/eclipse/jetty/embedded/LikeJettyXml.java
 *
 * @author duyetpt
 *
 */
public class DCAServerOnlyHttp {

    private final static Logger logger = LoggerFactory.getLogger(DCAServerOnlyHttp.class);

    public static void start() {
        logger.info("starting config server...");

		// === jetty.xml ===
        // Setup Threadpool
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(1500);

        // Server
        Server server = new Server(threadPool);

        // Scheduler
        server.addBean(new ScheduledExecutorScheduler());

        // HTTP Configuration
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(8443);
        http_config.setOutputBufferSize(32768);
        http_config.setRequestHeaderSize(8192);
        http_config.setResponseHeaderSize(8192);
        http_config.setSendServerVersion(true);
        http_config.setSendDateHeader(false);
		// httpConfig.addCustomizer(new ForwardedRequestCustomizer());

		// Handler Structure
        // Extra options
        server.setDumpAfterStart(false);
        server.setDumpBeforeStop(false);
        server.setStopAtShutdown(true);

        // config for jetty server
        ResourceConfig resourceConfig = new ResourceConfig();
        // regist for @Path and @Provider with SPI
        resourceConfig.packages("com.vn.dailycookapp.service", "com.vn.dailycookapp.auth",
                "com.vn.dailycookapp.security");
        resourceConfig.register(MultiPartFeature.class);	// Enable
        // Multipart/form-data
        // feature

        ServletContainer container = new ServletContainer(resourceConfig);

        ServletHolder holder = new ServletHolder(container);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(holder, "/*");
		// server.setHandler(contextHandler);

        HandlerCollection handlers = new HandlerCollection();
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(contextHandler);
        handlers.setHandlers(new Handler[]{contexts, new DefaultHandler()});
        server.setHandler(handlers);

        // === jetty-http.xml ===
        ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(http_config));
        http.setPort(8998);
        http.setIdleTimeout(30000);
        server.addConnector(http);

        // === jetty-lowresources.xml ===
        LowResourceMonitor lowResourcesMonitor = new LowResourceMonitor(server);
        lowResourcesMonitor.setPeriod(1000);
        lowResourcesMonitor.setLowResourcesIdleTimeout(200);
        lowResourcesMonitor.setMonitorThreads(true);
        lowResourcesMonitor.setMaxConnections(0);
        lowResourcesMonitor.setMaxMemory(0);
        lowResourcesMonitor.setMaxLowResourcesTime(5000);
        server.addBean(lowResourcesMonitor);

        try {
            // start servlet container
            server.start();
            System.out.println("Start server ....");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
