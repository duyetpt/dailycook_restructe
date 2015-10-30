package com.vn.dailycookapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.internal.util.Base64;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.json.JsonTransformer;
import org.junit.BeforeClass;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.vn.dailycookapp.security.authentication.CurrentUser;
import com.vn.dailycookapp.security.authentication.LoginMethod;
import com.vn.dailycookapp.service.HeaderField;
import com.vn.dailycookapp.service.RecipeService;
import com.vn.dailycookapp.service.UserService;
import com.vn.dailycookapp.utils.ConfigurationLoader;
import com.vn.dailycookapp.utils.ErrorCodeConstant;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongoImportExecutable;
import de.flapdoodle.embed.mongo.MongoImportProcess;
import de.flapdoodle.embed.mongo.MongoImportStarter;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.IMongoImportConfig;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongoImportConfigBuilder;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.config.Timeout;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;

public class AbstractTest extends JerseyTest {
	protected String				responseData;
	
	static Command					command				= Command.MongoD;
	protected static final String	DATABASE_NAME		= "dailycook";
	protected static final int		PORT				= 17017;
	
	private static MongodProcess	_mongod;
	protected static MongoDatabase  _mongo;
	
	static IRuntimeConfig			runtimeConfig		= new RuntimeConfigBuilder()
																.defaults(command)
																.artifactStore(
																		new ExtractedArtifactStoreBuilder()
																				.defaults(command)
																				.download(
																						new DownloadConfigBuilder()
																								.defaultsForCommand(
																										command)
																								.build())
																				.executableNaming(new UserTempNaming()))
																.build();
	
	static MongodStarter			runtime				= MongodStarter.getInstance(runtimeConfig);
	static MongodExecutable			mongodExecutable	= null;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void set() throws Exception {
		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(PORT, Network.localhostIsIPv6())).build();
		mongodExecutable = runtime.prepare(mongodConfig);
		_mongod = mongodExecutable.start();
		 _mongo = new MongoClient("localhost", PORT).getDatabase("dailycook");
	}
	
	@org.junit.AfterClass
	public static void tear() {
		_mongod.stop();
		mongodExecutable.stop();
	}
	
	@Override
	protected Application configure() {
		ConfigurationLoader.getInstance()
				.setLanguagePath(getClass().getResource("/lang/").getPath().substring(1));
		return new ResourceConfig(UserService.class, RecipeService.class);
	}
	
	protected final JSONObject getResponse() {
		JSONObject jsonObj = new JSONObject(responseData);
		int errorCode = jsonObj.getInt("error_no");
		assertEquals(ErrorCodeConstant.SUCCESSUL.getErrorCode(), errorCode);
		
		return jsonObj;
	}
	
	/**
	 * import data for test but not close mongod
	 * 
	 * @param collectionName
	 * @param dataFile
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public final void importData(String collectionName, String dataFile) throws UnknownHostException, IOException {
		
		startMongoImport(PORT, DATABASE_NAME, collectionName, dataFile, false, true, true);
	}
	
	private final MongoImportProcess startMongoImport(int port, String dbName, String collection, String jsonFile,
			Boolean jsonArray, Boolean upsert, Boolean drop) throws UnknownHostException, IOException {
		IMongoImportConfig mongoImportConfig = new MongoImportConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(port, Network.localhostIsIPv6())).db(dbName).collection(collection).upsert(upsert)
				.dropCollection(drop).jsonArray(jsonArray).importFile(jsonFile).timeout(new Timeout(40000)).build();
		MongoImportExecutable mongoImportExecutable = MongoImportStarter.getDefaultInstance()
				.prepare(mongoImportConfig);
		MongoImportProcess mongoImport = mongoImportExecutable.start();
		return mongoImport;
	}
	
	/**
	 * Get token for other test, include import user data
	 */
	public String getToken() {
		try {
			importData("User", getClass().getResource("/User.json").getFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String email = "ki1@gmail.com";
		String password = "123456789";
		String token = email + ":" + password;
		String token64 = Base64.encodeAsString(token);
		String authInfo = "Basic " + token64;
		
		String response = target("dailycook/user/login").request(MediaType.APPLICATION_JSON_TYPE)
				.header(HeaderField.AUTHORIZATION, authInfo)
				.header(HeaderField.LOGIN_METHOD, LoginMethod.EMAIL_PASSWORD).post(null, String.class);
		
		JSONObject jsonObj = new JSONObject(response);
		int errorCode = jsonObj.getInt("error_no");
		assertEquals(ErrorCodeConstant.SUCCESSUL.getErrorCode(), errorCode);
		CurrentUser user = JsonTransformer.getInstance().unmarshall(jsonObj.getJSONObject("data").toString(),
				CurrentUser.class);
		assertNotNull(user.getToken());
		
		return user.getToken();
	}
	
}
