package askfm.test.bind;

import java.io.IOException;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;

import askfm.impl.properties.DatabasePropertiesImpl;
import askfm.impl.question.BlacklistValidationServiceImpl;
import askfm.server.bind.ORMModule;
import askfm.server.bind.PropertiesModule;
import askfm.server.bind.ServiceModule;

public class TestUtils {
	private static final String BLACKLIST_RESOURCE = "/test-blacklist.txt";

	public static Injector getTestInjector() throws IOException {

		Properties properties = new Properties();
		properties.put(DatabasePropertiesImpl.SERVER_DATABASE_PROPERTY, "jdbc:sqlite::memory:");
		properties.put(DatabasePropertiesImpl.DATABASE_DRIVER_PROPERTY, "org.sqlite.JDBC");
		properties.put(BlacklistValidationServiceImpl.BLACKLIST_FILE_NAME,
				ClassLoader.class.getResource(BLACKLIST_RESOURCE).getFile());

		return Guice.createInjector(new PropertiesModule(properties), new ORMModule(), new ServiceModule(),
				new ThirdPartyModule());
	}
}
