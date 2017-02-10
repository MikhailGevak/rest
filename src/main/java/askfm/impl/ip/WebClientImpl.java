package askfm.impl.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.http.HttpStatus;

import com.google.inject.Inject;

import askfm.api.GeneralException;
import askfm.api.ServiceException;
import askfm.api.properties.PropertyService;

public class WebClientImpl implements WebClient {
	private static final String HOST_PROPERTY = "askfm.ipinfo.host";
	private final String host;

	@Inject
	public WebClientImpl(PropertyService propertyService) {
		host = propertyService.getPropertyValue(HOST_PROPERTY);

	}

	@Override
	public String getIpInfo(String ip) throws ServiceException {
		try {
			URL url = new URL(host + "/" + ip);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			if (responseCode == HttpStatus.OK_200) {
				return response.toString();
			} else {
				throw new GeneralException("Connection error: " + con);
			}
		} catch (IOException ex) {
			throw new GeneralException(ex);
		}
	}

}
