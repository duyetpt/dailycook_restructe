package org.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;

public class ConnectionDAO {

    public static final String CONFIG_FOR_WEB_SERVICE = "web_service";
    public static final String CONFIG_FOR_WEB_BACKEND = "web_backend";
    private final static Logger logger = LoggerFactory.getLogger(ConnectionDAO.class);

//    public static String DB_HOST = "localhost";
    public static String DB_HOST = "dailycook.cloudapp.net";
    public static String CONFIG_FOR = CONFIG_FOR_WEB_BACKEND;

    private static final String DBNAME = "dailycook";
    private static Morphia morphia;
    private static Datastore datastore;

    private static void config() {
        logger.info(("Connect mongoDB..."));

        morphia = new Morphia();

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("org.entity");

        MongoClientOptions mongoClientOpts = null;
        if (CONFIG_FOR.equals(CONFIG_FOR_WEB_BACKEND)) {
            mongoClientOpts = MongoClientOptions.builder().maxConnectionIdleTime(2).connectTimeout(60000)
                    .connectionsPerHost(100).build();
        } else {
            mongoClientOpts = MongoClientOptions.builder().connectTimeout(6000)
                    .connectionsPerHost(100).build();
        }
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
