package askfm.test.bind;

import java.io.IOException;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;

import askfm.server.bind.ORMModule;
import askfm.server.bind.PropertiesModule;
import askfm.server.bind.ServiceModule;

public class TestUtils {
	private static final String PROPERTIES_RESOURCE = "/test.properties";
	private static Injector injector;

	public static Injector getTestInjector() throws IOException {
		if (injector == null) {
			Properties properties = new Properties();
			properties.load(ClassLoader.class.getResourceAsStream(PROPERTIES_RESOURCE));
			injector = Guice.createInjector(new PropertiesModule(properties), new ORMModule(), new ServiceModule(),
					new ThirdPartyModule());
		}
		return injector;
	}
}
