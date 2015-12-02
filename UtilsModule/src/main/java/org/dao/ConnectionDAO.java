package org.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

public class ConnectionDAO {
	private final static Logger logger = LoggerFactory.getLogger(ConnectionDAO.class);
	
	public static String DB_HOST = "localhost";
        
	private static final String DBNAME = "dailycook";
	private static Morphia morphia;
	private static Datastore datastore;
	
	private static void config() {
		logger.info(("Connect mongoDB..."));
		
		morphia = new Morphia();
		
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("com.vn.dailycookapp.entity");
                
                MongoClientOptions mongoClientOpts = MongoClientOptions.builder().connectTimeout(30000).socketTimeout(60000)
                                                        .connectionsPerHost(400).threadsAllowedToBlockForConnectionMultiplier(20).build();
		MongoClient mongoClient = new MongoClient(DB_HOST, mongoClientOpts);
//		MongoClient mongoClient = new MongoClient("dailycook.cloudapp.net");
		
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
