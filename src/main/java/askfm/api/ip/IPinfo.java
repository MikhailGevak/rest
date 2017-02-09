package askfm.api.ip;

import java.math.BigDecimal;

public class IPinfo {
	public final String ip;
	public final Country country;
	public final Region region;
	public final String city;
	public final String zipCode;
	public final String timeZone;
	public final Integer metroCode;
	public final Coordinate coordinate;

	public IPinfo(String ip, Country country, Region region, String city, String zipCode, String timeZone,
			Integer metroCode, Coordinate coordinate) {
		this.ip = ip;
		this.country = country;
		this.region = region;
		this.city = city;
		this.zipCode = zipCode;
		this.timeZone = timeZone;
		this.metroCode = metroCode;
		this.coordinate = coordinate;
	}

	public static class Country {
		public final String code;
		public final String name;

		public Country(String code, String name) {
			this.code = code;
			this.name = name;
		}
	}

	public static class Region {
		public final String code;
		public final String name;

		public Region(String code, String name) {
			this.code = code;
			this.name = name;
		}
	}

	public static class Coordinate {
		public final BigDecimal latitude;
		public final BigDecimal longitude;
		
		public Coordinate(BigDecimal latitude, BigDecimal longitude){
			this.longitude = longitude;
			this.latitude = latitude;
		}
	}

	public static Builder builder(){
		return new Builder();
	}
	public static class Builder {
		private String ip;
		private Country country;
		private Region region;
		private String city;
		private String zipCode;
		private String timeZone;
		private Integer metroCode;
		private Coordinate coordinate;
		
		public Builder setIp(String ip){
			this.ip = ip;
			return this;
		}
		
		public Builder setCountry(Country country){
			this.country = country;
			return this;
		}
		
		public Builder setRegion(Region region){
			this.region = region;
			return this;
		}
		
		public Builder setCity(String city){
			this.city = city;
			return this;
		}
		
		public Builder setZipCode(String zipCode){
			this.zipCode = zipCode;
			return this;
		}
		
		public Builder setTimeZone(String timeZone){
			this.timeZone = timeZone;
			return this;
		}
		
		public Builder setMetroCode(Integer metroCode){
			this.metroCode = metroCode;
			return this;
		}
		
		public Builder setCoordinate(Coordinate coordinate){
			this.coordinate = coordinate;
			return this;
		}
		
		public IPinfo build(){
			return new IPinfo(ip, country, region, city, zipCode, timeZone, metroCode, coordinate);
		}
	}
}