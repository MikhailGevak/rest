package askfm.impl.blacklist;

import java.io.IOException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import askfm.api.question.BlacklistValidationService;
import askfm.impl.question.QuestionImpl;
import askfm.test.bind.TestUtils;

import static org.junit.Assert.*;

public class BlacklistServiceImplTest {
	private static String VALID_TEXT = "ewfwefwef wrr35456gwrggwe sff;23bklfsnbbbs wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw  \tp[qwejgjwpjgpw";
	private static String NOT_VALID_TEXT1 = "ewfwefwef wrrgwrggwe sff;bklfsnbbbs terrorism wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw  \tp[qwejgjwpjgpw";
	private static String NOT_VALID_TEXT2 = "ewfwefwef wrrgwrggwe terrorism sff;bklfsnbbbs  wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw ISIS  \tp[qwejgjwpjgpw";
	private static String NOT_VALID_TEXT3 = "ewfwefwef wrrgwrggwe sff;bklfsnbbbs wqje ;wejttjwjffowf\nqpqejfpwojgrpwjjgw isiS \tp[qwejgjwpjgpw";

	private BlacklistValidationService blackListService;

	@Before
	public void before() throws IOException {
		blackListService = TestUtils.getTestInjector().getInstance(BlacklistValidationService.class);
	}

	@Test
	public void isValid() {
		String result = blackListService.doValidation(new QuestionImpl(VALID_TEXT, "UA", new Date()));

		assertEquals("", result);
	}

	@Test
	public void isNotValid1() {
		String result = blackListService.doValidation(new QuestionImpl(NOT_VALID_TEXT1, "UA", new Date()));
		assertEquals("terrorism", result);
	}

	@Test
	public void isNotValid2() {
		String result = blackListService.doValidation(new QuestionImpl(NOT_VALID_TEXT2, "UA", new Date()));
		assertEquals("terrorism", result);
	}

	@Test
	public void isNotValid3() {
		String result = blackListService.doValidation(new QuestionImpl(NOT_VALID_TEXT3, "UA", new Date()));
		assertEquals("isis", result);
	}
}
