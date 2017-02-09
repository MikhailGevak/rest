package askfm.impl;

import java.sql.SQLException;
import java.util.List;

import askfm.api.entity.Entity;
import askfm.api.entity.EntityService;
import askfm.api.ServiceException;

public abstract class AbstractServiceImpl<I extends E, E extends Entity> implements EntityService<E> {
	protected final AbstractDAO<I, E> dao;

	public AbstractServiceImpl(AbstractDAO<I, E> dao) throws ServiceException {
		this.dao = dao;
		initialPrepareData();
	}

	@Override
	public E createEntity(E entity) throws ServiceException {
		try {
			return dao.createIfNotExists(entity);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public E getEntityById(Integer id) throws ServiceException {
		try {
			return dao.queryForId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}

	}

	@Override
	public void initialPrepareData() throws ServiceException {
		try {
			dao.initialPrepareData();
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
	}

	@Override
	public List<? extends E> getAll() throws ServiceException {
		try {
			return dao.getAll();
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
	}
}