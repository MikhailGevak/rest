package askfm.impl.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import askfm.api.properties.DatabaseProperties;
import askfm.api.properties.PropertyService;
import askfm.api.properties.ServerProperties;
import askfm.api.properties.ServletParameters;

public class PropertyServiceImpl implements PropertyService {
	private Properties properties;
	private ServerProperties serverProperties;
	private DatabaseProperties databaseProperties;
	private ServletParameters servletParameters;
	
	public PropertyServiceImpl(){
		load(new Properties());
	}
	
	public PropertyServiceImpl(InputStream stream) throws IOException{
		Properties properties = new Properties();
		properties.load(stream);
		load(properties);
	}
	
	public PropertyServiceImpl(Properties properties){
		load(properties);
	}

	public void load(Properties properties){
		this.properties = properties;
		this.databaseProperties = new DatabasePropertiesImpl(properties);
		this.serverProperties = new ServerPropertiesImpl(properties);
		this.servletParameters = new ServletParametersImpl(properties);
	}
	
	@Override
	public String getPropertyValue(String name){
		return properties.getProperty(name);
	}
	
	@Override
	public ServerProperties getServerProperties(){
		return serverProperties;
	}
	
	@Override
	public DatabaseProperties getDatabaseProperties() {
		return databaseProperties;
	}

	@Override
	public ServletParameters getServletParameters() {
		return servletParameters;
	}
}
