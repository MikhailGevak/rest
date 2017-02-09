package askfm.impl.ip;

import com.google.gson.Gson;
import com.google.inject.Inject;

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
	public IPinfo getInfoByIp(String ip) {
		String json = webClient.getIpInfo(ip);
		IpInfoResponse response = gson.fromJson(json, IpInfoResponse.class);
		return convertResponse(response);
	}

	private IPinfo convertResponse(IpInfoResponse ipInfoResponse){
		return IPinfo.builder().setCity(ipInfoResponse.city)
		.setCountry(new IPinfo.Country(ipInfoResponse.country_code, ipInfoResponse.country_name))
		.setIp(ipInfoResponse.ip)
		.setMetroCode(ipInfoResponse.metro_code)
		.setRegion(new IPinfo.Region(ipInfoResponse.region_code,ipInfoResponse.region_name))
		.setTimeZone(ipInfoResponse.time_zone)
		.setZipCode(ipInfoResponse.zip_code)
		.setCoordinate(new IPinfo.Coordinate(ipInfoResponse.latitude,ipInfoResponse.longitude))
		.build();
	}
}
