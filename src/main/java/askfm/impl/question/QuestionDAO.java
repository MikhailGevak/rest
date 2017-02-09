package askfm.impl.question;

import java.sql.SQLException;

import com.google.inject.Inject;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import askfm.api.question.Question;
import askfm.impl.AbstractDAO;

public class QuestionDAO extends AbstractDAO<QuestionImpl, Question> {

	@Inject
	public QuestionDAO(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, QuestionImpl.class);
	}

	@Override
	public void initialPrepareData() throws SQLException {
		TableUtils.createTableIfNotExists(connectionSource, QuestionImpl.class);
	}

	@Override
	protected QuestionImpl toImpl(Question data) {
		if (data == null) return null;
		return (QuestionImpl) data;
	}

}
