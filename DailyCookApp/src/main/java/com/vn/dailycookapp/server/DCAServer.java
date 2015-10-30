package com.vn.dailycookapp.server;

import java.io.File;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ScheduledExecutorScheduler;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.notification.NotificationWorker;

/**
 * http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/examples/embedded/src/main/java/org/eclipse/jetty/embedded/LikeJettyXml.java
 * @author duyetpt
 *
 */
public class DCAServer {
	private final static Logger	logger	= LoggerFactory.getLogger(DCAServer.class);
	
	/**
	 * 
	 * @param args
	 *            : args[0] : multi language folder
	 */
	public static void main(String[] args) {
		logger.info("starting config server...");
		
		// Path to as-built jetty-distribution directory
		String jettyHomeBuild = "/opt/jetty_home";
		
		// Find jetty home and base directories
		String homePath = System.getProperty("jetty.home", jettyHomeBuild);
		File homeDir = new File(homePath);
		if (!homeDir.exists()) {
			logger.error((homeDir.getAbsolutePath()));
			return;
		}
		
		// Configure jetty.home and jetty.base system properties
		String jetty_home = homeDir.getAbsolutePath();
		System.setProperty("jetty.home", jetty_home);
		
		// === jetty.xml ===
		// Setup Threadpool
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMaxThreads(500);
		
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
//		server.setHandler(contextHandler);
		
		HandlerCollection handlers = new HandlerCollection();
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.addHandler(contextHandler);
		handlers.setHandlers(new Handler[] { contexts, new DefaultHandler() });
		server.setHandler(handlers);

        // === jetty-http.xml ===
        ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(http_config));
        http.setPort(8998);
        http.setIdleTimeout(30000);
        server.addConnector(http);
		
     // === jetty-https.xml ===
        // SSL Context Factory
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(jetty_home + "/keystore");
        sslContextFactory.setKeyStorePassword("123@123a");
        sslContextFactory.setKeyManagerPassword("123@123a");
        sslContextFactory.setTrustStorePath(jetty_home + "/keystore");
        sslContextFactory.setTrustStorePassword("123@123a");
        sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA",
                "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
                "SSL_RSA_EXPORT_WITH_RC4_40_MD5",
                "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");

        // SSL HTTP Configuration
        HttpConfiguration https_config = new HttpConfiguration(http_config);
        https_config.addCustomizer(new SecureRequestCustomizer());

        // SSL Connector
        ServerConnector sslConnector = new ServerConnector(server,
            new SslConnectionFactory(sslContextFactory,HttpVersion.HTTP_1_1.asString()),
            new HttpConnectionFactory(https_config));
        sslConnector.setPort(8181);
        server.addConnector(sslConnector);
        
     // === jetty-requestlog.xml ===
        NCSARequestLog requestLog = new NCSARequestLog();
        requestLog.setFilename(jetty_home + "/logs/yyyy_mm_dd.request.log");
        requestLog.setFilenameDateFormat("yyyy_MM_dd");
        requestLog.setRetainDays(90);
        requestLog.setAppend(true);
        requestLog.setExtended(true);
        requestLog.setLogCookies(false);
        requestLog.setLogTimeZone("GMT");
        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
        handlers.addHandler(requestLogHandler);

        // === jetty-lowresources.xml ===
        LowResourceMonitor lowResourcesMonitor=new LowResourceMonitor(server);
        lowResourcesMonitor.setPeriod(1000);
        lowResourcesMonitor.setLowResourcesIdleTimeout(200);
        lowResourcesMonitor.setMonitorThreads(true);
        lowResourcesMonitor.setMaxConnections(0);
        lowResourcesMonitor.setMaxMemory(0);
        lowResourcesMonitor.setMaxLowResourcesTime(5000);
        server.addBean(lowResourcesMonitor);
        
		try {
			server.start();
			System.out.println("Start server ....");
			server.join();
			
			// start notification worker
			NotificationWorker worker = new NotificationWorker();
			worker.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
