package askfm.impl.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import askfm.api.properties.PropertyService;

public class PropertyServiceImpl implements PropertyService {
	private Properties properties;

	public PropertyServiceImpl() {
		load(new Properties());
	}

	public PropertyServiceImpl(InputStream stream) throws IOException {
		Properties properties = new Properties();
		properties.load(stream);
		load(properties);
	}

	public PropertyServiceImpl(Properties properties) {
		load(properties);
	}

	public void load(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String getPropertyValue(String name) {
		return properties.getProperty(name);
	}
}
