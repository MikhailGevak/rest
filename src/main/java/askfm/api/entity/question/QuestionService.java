package askfm.api.entity.question;

import java.sql.SQLException;
import java.util.List;

import askfm.api.ServiceException;
import askfm.api.entity.EntityService;

public interface QuestionService extends EntityService<Question>{
	List<? extends Question> getByCountry(String country) throws SQLException;
	Question createAndSave(String text, String country) throws ServiceException;
}
