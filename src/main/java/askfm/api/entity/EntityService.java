package askfm.api.entity;

import java.util.List;
import askfm.api.ServiceException;

public interface EntityService<E extends Entity> {
	void initialPrepareData() throws ServiceException;

	E createEntity(E entity) throws ServiceException;

	E getEntityById(Integer id) throws ServiceException;
	
	List<? extends E> getAll() throws ServiceException;
}
