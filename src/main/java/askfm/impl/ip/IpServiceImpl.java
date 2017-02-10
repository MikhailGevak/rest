package askfm.impl.ip;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.inject.Inject;

import askfm.api.GeneralException;
import askfm.api.ServiceException;
import askfm.api.ip.IPinfo;
import askfm.api.ip.IpService;

public class IpServiceImpl implements IpService {
	private WebClient webClient;
	private Gson gson = new Gson();

	@Inject
	public IpServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public IPinfo getInfoByIp(String ip) throws ServiceException {
		String json;
		try {
			json = webClient.getIpInfo(ip);
		} catch (IOException e) {
			throw new GeneralException(e);
		}
		IpInfoResponse response = gson.fromJson(json, IpInfoResponse.class);
		return convertResponse(response);
	}

	private IPinfo convertResponse(IpInfoResponse ipInfoResponse) {
		return IPinfo.builder().setCity(ipInfoResponse.city)
				.setCountry(new IPinfo.Country(ipInfoResponse.country_code, ipInfoResponse.country_name))
				.setIp(ipInfoResponse.ip).setMetroCode(ipInfoResponse.metro_code)
				.setRegion(new IPinfo.Region(ipInfoResponse.region_code, ipInfoResponse.region_name))
				.setTimeZone(ipInfoResponse.time_zone).setZipCode(ipInfoResponse.zip_code)
				.setCoordinate(new IPinfo.Coordinate(ipInfoResponse.latitude, ipInfoResponse.longitude)).build();
	}
}
