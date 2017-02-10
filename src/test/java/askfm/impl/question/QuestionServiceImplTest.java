package askfm.impl.question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Injector;
import askfm.api.ServiceException;
import askfm.api.question.Question;
import askfm.api.question.QuestionService;
import askfm.test.bind.TestUtils;

import static org.junit.Assert.*;

public class QuestionServiceImplTest {
	private QuestionService questionService;

	@Before
	public void before() throws IOException {
		questionService = TestUtils.getTestInjector().getInstance(QuestionService.class);
	}

	@Test
	public void createAndSaveTest() throws ServiceException {
		Question question = questionService.createAndSave("lalalalalalalalalala", "UA");

		assertNotNull(question);
		assertEquals("lalalalalalalalalala", question.getText());
		assertEquals("UA", question.getCountry());
		assertNotNull(question.getId());
		assertNotNull(question.getDateTime());
	}

	@Test
	public void getAllTest() throws ServiceException {
		questionService.createAndSave("lalalalalalalalalala", "UA");
		questionService.createAndSave("lalalalalalalalalala", "US");

		List<? extends Question> questions = questionService.getAll();
		assertNotNull(questions);
		assertEquals(2, questions.size());
	}

	@Test
	public void getById() throws ServiceException {
		Question question = questionService.createAndSave("lalalalalalalalalala", "UA");
		Integer id = question.getId();

		question = questionService.getEntityById(id);
		assertNotNull(question);
		assertEquals("lalalalalalalalalala", question.getText());
		assertEquals("UA", question.getCountry());
		assertNotNull(question.getId());
		assertNotNull(question.getDateTime());
	}

	@Test
	public void getByCountry() throws ServiceException, SQLException {
		questionService.createAndSave("lalalalalalalalalala", "UA");
		questionService.createAndSave("lalalalalalalalalala", "UA");
		questionService.createAndSave("lalalalalalalalalala", "US");

		List<? extends Question> questions = questionService.getByCountry("UA");
		assertNotNull(questions);
		assertEquals(2, questions.size());
	}
}
