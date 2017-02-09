package askfm.api;

import java.util.List;

public interface EntityService<E extends Entity> {
	void initialPrepareData() throws ServiceException;

	E createEntity(E entity) throws ServiceException;

	E getEntityById(Integer id) throws ServiceException;
	
	List<? extends E> getAll() throws ServiceException;
}
