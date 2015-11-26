package org.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

class ConnectionDAO {
	
	private final static Logger logger = LoggerFactory.getLogger(ConnectionDAO.class);
	
	private static final String DBNAME = "dailycook";
	private static Morphia morphia;
	private static Datastore datastore;
	
	private static void config() {
		logger.info(("Connect mongoDB..."));
		
		morphia = new Morphia();
		
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("com.vn.dailycookapp.entity");

//		MongoClient mongoClient = new MongoClient("localhost");
		MongoClient mongoClient = new MongoClient("dailycook.cloudapp.net");
		
		// create the Datastore connecting to the default port on the local host
		datastore = morphia.createDatastore(mongoClient, DBNAME);
		
		logger.info(("Connect " + DBNAME + "database success ..."));
	}
	
	public static Datastore getDataStore() {
		if (datastore == null) {
			config();
		}
		
		return datastore;
	}
}
