package askfm.impl.blacklist;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import askfm.api.blacklist.BlacklistService;
import askfm.test.bind.TestUtils;

import static org.junit.Assert.*;

public class BlacklistServiceImplTest {
	private static String VALID_TEXT = "ewfwefwef wrr35456gwrggwe sff;23bklfsnbbbs wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw  \tp[qwejgjwpjgpw";
	private static String NOT_VALID_TEXT1 = "ewfwefwef wrrgwrggwe sff;bklfsnbbbs terrorism wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw  \tp[qwejgjwpjgpw";
	private static String NOT_VALID_TEXT2 = "ewfwefwef wrrgwrggwe terrorism sff;bklfsnbbbs  wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw ISIS  \tp[qwejgjwpjgpw";
	private static String NOT_VALID_TEXT3 = "ewfwefwef wrrgwrggwe sff;bklfsnbbbs wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw isiS \tp[qwejgjwpjgpw";

	private BlacklistService blackListService;

	@Before
	public void before() throws IOException {
		blackListService = TestUtils.getTestInjector().getInstance(BlacklistService.class);
	}

	@Test
	public void isValid() {
		String result = blackListService.isValid(VALID_TEXT);

		assertEquals("", result);
	}

	@Test
	public void isNotValid1() {
		String result = blackListService.isValid(NOT_VALID_TEXT1);
		assertEquals("terrorism", result);
	}

	@Test
	public void isNotValid2() {
		String result = blackListService.isValid(NOT_VALID_TEXT2);
		assertEquals("terrorism", result);
	}

	@Test
	public void isNotValid3() {
		String result = blackListService.isValid(NOT_VALID_TEXT3);
		assertEquals("isis", result);
	}
}
