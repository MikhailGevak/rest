package askfm.server.bind;

import java.util.Properties;

import com.google.inject.Binder;
import com.google.inject.Module;
import askfm.api.properties.DatabaseProperties;
import askfm.api.properties.PropertyService;
import askfm.api.properties.ServerProperties;
import askfm.impl.properties.DatabasePropertiesImpl;
import askfm.impl.properties.PropertyServiceImpl;
import askfm.impl.properties.ServerPropertiesImpl;

public class PropertiesModule implements Module {
	final private Properties properties;

	public PropertiesModule(Properties properties) {
		this.properties = properties;
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(DatabaseProperties.class).to(DatabasePropertiesImpl.class);
		binder.bind(PropertyService.class).toInstance(new PropertyServiceImpl(properties));
		binder.bind(ServerProperties.class).to(ServerPropertiesImpl.class);
	}

}
