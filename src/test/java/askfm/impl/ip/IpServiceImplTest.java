package askfm.impl.ip;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;

import askfm.api.ip.IPinfo;
import askfm.api.ip.IpService;
import askfm.test.bind.TestUtils;

import static org.junit.Assert.*;

public class IpServiceImplTest {
	private IpService ipService;
	private Injector injector;

	@Before
	public void before() throws IOException {
		injector = TestUtils.getTestInjector();
		ipService = injector.getInstance(IpService.class);
	}

	@Test
	public void getIpInfo(){
		IPinfo ipInfo = ipService.getInfoByIp("0.0.0.0");
		
		assertEquals("San Francisco", ipInfo.city);
		assertEquals(BigDecimal.valueOf(37.7697), ipInfo.coordinate.latitude);
		assertEquals(BigDecimal.valueOf(-122.3933), ipInfo.coordinate.longitude);		
		assertEquals("US", ipInfo.country.code);
		assertEquals("United States", ipInfo.country.name);
		assertEquals("192.30.253.113", ipInfo.ip);
		assertEquals(Integer.valueOf(807), ipInfo.metroCode);
		assertEquals("CA", ipInfo.region.code);
		assertEquals("California", ipInfo.region.name);
		assertEquals("America/Los_Angeles", ipInfo.timeZone);
		assertEquals("94107", ipInfo.zipCode);		
	}
}
