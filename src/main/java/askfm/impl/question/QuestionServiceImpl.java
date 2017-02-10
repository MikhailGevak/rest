package askfm.impl.question;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.inject.Inject;

import askfm.api.GeneralException;
import askfm.api.NotValidEntityException;
import askfm.api.ServiceException;
import askfm.api.blacklist.BlacklistService;
import askfm.api.question.Question;
import askfm.api.question.QuestionService;
import askfm.impl.AbstractServiceImpl;

public class QuestionServiceImpl extends AbstractServiceImpl<QuestionImpl, Question> implements QuestionService {
	public static String ENTITY_NAME = "Question";
	
	private BlacklistService blacklistService;
	
	@Inject
	public QuestionServiceImpl(QuestionDAO dao, BlacklistService blacklistService) throws ServiceException {
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
		String forbidden = blacklistService.isValid(entity.getText());
		if (StringUtils.isEmpty(forbidden)) return;
		
		throw new NotValidEntityException("Your text contains forbidden word: " + forbidden, getEntityName());
	}
}
