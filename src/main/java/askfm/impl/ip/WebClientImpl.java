package askfm.impl.ip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.http.HttpStatus;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import askfm.api.ServiceException;
import askfm.api.properties.PropertyService;

@Singleton
public class WebClientImpl implements WebClient {
	private static final String HOST_PROPERTY = "askfm.ipinfo.host";
	private final String host;

	@Inject
	public WebClientImpl(PropertyService propertyService) {
		this(propertyService.getPropertyValue(HOST_PROPERTY));
	}

	public WebClientImpl(String host) {
		this.host = host;
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

			String responseStr = response.toString();
			if (responseCode == HttpStatus.OK_200) {
				return responseStr;
			} else {
				System.out.println(host + " returned " + responseStr + " (" + responseCode
						+ ").");
				return "";
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return "";
		}
	}

}
