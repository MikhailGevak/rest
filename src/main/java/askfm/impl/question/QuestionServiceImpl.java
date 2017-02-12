package askfm.impl.question;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import askfm.api.ServiceException;
import askfm.api.exceptions.GeneralException;
import askfm.api.exceptions.NotValidEntityException;
import askfm.api.question.BlacklistValidationService;
import askfm.api.question.Question;
import askfm.api.question.QuestionService;
import askfm.impl.AbstractServiceImpl;

public class QuestionServiceImpl extends AbstractServiceImpl<QuestionImpl, Question> implements QuestionService {
	public static String ENTITY_NAME = "Question";
	
	private BlacklistValidationService blacklistService;
	
	@Inject
	public QuestionServiceImpl(QuestionDAO dao, BlacklistValidationService blacklistService) throws ServiceException {
		super(dao);
		this.blacklistService = blacklistService;
	}

	@Override
	public List<? extends Question> getByCountry(String country) throws ServiceException {
		try {
			return dao.queryForEq(QuestionImpl.COUNTRY_FIELD, country);
		} catch (SQLException ex) {
			throw new GeneralException(ex.getMessage(), ex);
		}
	}

	@Override
	public Question createAndSave(String text, String country) throws ServiceException {
		Question question = new QuestionImpl(text, country, new Date());
		return createEntity(question);
	}

	@Override
	protected String getEntityName() {
		return ENTITY_NAME;
	}

	@Override
	protected void checkEntity(Question entity) throws NotValidEntityException {
		String forbidden = blacklistService.doValidation(entity);
		if (StringUtils.isEmpty(forbidden)) return;
		
		throw new NotValidEntityException("Your text contains forbidden word: " + forbidden, getEntityName());
	}
}
