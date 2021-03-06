package askfm.impl.properties;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import askfm.api.properties.PropertyService;
import askfm.api.properties.ServerProperties;

@Singleton
public class ServerPropertiesImpl implements ServerProperties {
	public static final String SERVER_PORT_PROPERTY = "askfm.server.port";
	public static final String CONTEXT_PATH_PORT_PROPERTY = "askfm.server.context_path";
	public static final String SERVER_HOST_NAME_PROPERTY = "askfm.server.host_name";

	private String port;
	private String contextPath;
	private String hostName;

	@Inject
	public ServerPropertiesImpl(PropertyService propertyService) {
		this(propertyService.getPropertyValue(SERVER_PORT_PROPERTY),
				propertyService.getPropertyValue(CONTEXT_PATH_PORT_PROPERTY),
				propertyService.getPropertyValue(SERVER_HOST_NAME_PROPERTY));
	}

	public ServerPropertiesImpl(String port, String contextPath, String hostName) {
		this.port = port;
		this.contextPath = contextPath;
		this.hostName = hostName;
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
	public String getHostName() {
		return hostName;
	}

	@Override
	public URI getServerURI() throws URISyntaxException {
		return new URI(getHostName() + ":" + getPort() + "/" + getContextPath());
	}
}
