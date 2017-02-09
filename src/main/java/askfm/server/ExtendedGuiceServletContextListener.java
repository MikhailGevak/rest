package askfm.server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import askfm.api.properties.ServerProperties;
import askfm.server.bind.GuiceBinderModule;
import askfm.server.bind.ORMModule;
import askfm.server.bind.PropertiesModule;
import askfm.server.bind.ServiceModule;
import askfm.server.bind.ThirdPartyModule;

public class ExtendedGuiceServletContextListener extends GuiceServletContextListener {
	private final Properties properties;
	private Injector injector;

	public ExtendedGuiceServletContextListener(Properties properties) {
		this.properties = properties;
	}

	@Override
	protected Injector getInjector() {
		if (injector == null) {
			try {
				injector = createInjector(properties);
			} catch (SQLException | IOException | ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return injector;
	}

	public Injector getGuiceInjector() {
		return injector;
	}

	protected Injector createInjector(Properties properties) throws SQLException, IOException, ClassNotFoundException {
		Injector injector = Guice.createInjector(new GuiceBinderModule(properties), new PropertiesModule(properties), new ORMModule(),
				new ServiceModule(), new ThirdPartyModule());

		return injector;
	}

	public URI getServerURI() throws URISyntaxException {
		return getInjector().getInstance(ServerProperties.class).getServerURI();
	}
}