package askfm.impl.properties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import askfm.api.properties.ServerProperties;

public class ServerPropertiesImpl implements ServerProperties{
	private static final String SERVER_PORT_PROPERTY = "askfm.server.port";
	private static final String CONTEXT_PATH_PORT_PROPERTY = "askfm.server.context_path";
	private static final String SERVER_HOST_NAME_PROPERTY = "askfm.server.host_name";
	
	private String port;
	private String contextPath;
	private String hostName;
	
	public ServerPropertiesImpl(Properties properties){
		load(properties);
	}
	
	public void load(Properties properties){
		this.port = properties.getProperty(SERVER_PORT_PROPERTY);
		this.contextPath = properties.getProperty(CONTEXT_PATH_PORT_PROPERTY);
		this.hostName = properties.getProperty(SERVER_HOST_NAME_PROPERTY);
	}
	
	@Override
	public String getPort() {
		return port;
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}

	@Override
	public String getHostName(){
		return hostName;
	}

	@Override
	public URI getServerURI() throws URISyntaxException {
		return new URI(getHostName() + ":" + getPort() + "/" + getContextPath());
	}
}
