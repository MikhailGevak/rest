package askfm.impl.ip;

public class WebClientMock implements WebClient {
	@Override
	public String getIpInfo(String ip){
		return "{\"ip\":\"192.30.253.113\",\"country_code\":\"US\",\"country_name\":\"United States\",\"region_code\":\"CA\",\"region_name\":\"California\",\"city\":\"San Francisco\",\"zip_code\":\"94107\",\"time_zone\":\"America/Los_Angeles\",\"latitude\":37.7697,\"longitude\":-122.3933,\"metro_code\":807}";
	}
}
