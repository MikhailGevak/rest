package askfm.impl.ip;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;

import askfm.api.properties.PropertyService;

public class WebClientImpl implements WebClient {
	private static final String HOST_PROPERTY = "askfm.ipinfo.host";
	private final WebTarget target;

	@Inject
	public WebClientImpl(PropertyService propertyService) {
		String host = propertyService.getPropertyValue(HOST_PROPERTY);
		Client client = ClientBuilder.newClient();
		this.target = client.target(host);
	}

	@Override
	public String getIpInfo(String ip) {
		return target.path("ip").request(MediaType.APPLICATION_JSON).get(String.class);
	}

}
