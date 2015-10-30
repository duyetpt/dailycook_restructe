package com.vn.dailycookapp.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vn.dailycookapp.notification.NotificationWorker;
import com.vn.dailycookapp.utils.ConfigurationLoader;

public class DCAServer {
	private final static Logger logger = LoggerFactory.getLogger(DCAServer.class);
	/**
	 * 
	 * @param args : args[0] : multi language folder
	 */
	public static void main(String[] args) {
		logger.info("starting config server...");
//		if (args == null || args.length == 0) {
//			logger.error("not has language folder, please add it as the first arguments !");
//			logger.warn("\\//=== Server stoped! ==\\//");
//			return;
//		}
//		String languageFolder = args[0];
//		if (languageFolder == null || languageFolder.isEmpty()) {
//			logger.error("not has language folder, please add it as the first arguments !");
//			logger.warn("\\//=== Server stoped! ==\\//");
//			return;
//		}
//		logger.warn("language_path: " + languageFolder);
		ConfigurationLoader.getInstance().setLanguagePath("");
		
		// config for jetty server
		ResourceConfig resourceConfig = new ResourceConfig();
		// regist for @Path and @Provider with SPI
		resourceConfig.packages("com.vn.dailycookapp.service", 
								"com.vn.dailycookapp.auth",
								"com.vn.dailycookapp.security");
		resourceConfig.register(MultiPartFeature.class);	// Enable Multipart/form-data feature

		ServletContainer container = new ServletContainer(resourceConfig);
		
		ServletHolder holder = new ServletHolder(container);
		ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		contextHandler.setContextPath("/");
		contextHandler.addServlet(holder, "/*");
		
		Server server = new Server(ConfigurationLoader.getInstance().getServerPort());
		server.setHandler(contextHandler);
		
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
