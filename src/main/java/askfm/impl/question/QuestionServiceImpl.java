package askfm.impl.question;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

import askfm.api.ServiceException;
import askfm.api.question.Question;
import askfm.api.question.QuestionService;
import askfm.impl.AbstractServiceImpl;

public class QuestionServiceImpl extends AbstractServiceImpl<QuestionImpl, Question> implements QuestionService {

	@Inject
	public QuestionServiceImpl(QuestionDAO dao) throws ServiceException {
		super(dao);
	}

	@Override
	public List<? extends Question> getByCountry(String country) throws ServiceException {
		try {
			return dao.queryForEq(QuestionImpl.COUNTRY_FIELD, country);
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public Question createAndSave(String text, String country) throws ServiceException {
		Question question = new QuestionImpl(text, country, new Date());
		return createEntity(question);
	}
}
