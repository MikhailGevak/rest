package askfm.api.question;

import java.util.List;

import askfm.api.EntityService;
import askfm.api.ServiceException;

public interface QuestionService extends EntityService<Question>{
	List<? extends Question> getByCountry(String country) throws ServiceException;
	Question createAndSave(String text, String country) throws ServiceException;
}
