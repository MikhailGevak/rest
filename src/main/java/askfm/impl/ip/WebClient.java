package askfm.impl.ip;

import java.io.IOException;
import java.net.MalformedURLException;

import askfm.api.ServiceException;

public interface WebClient {
	String getIpInfo(String ip) throws MalformedURLException, IOException, ServiceException;
}
