package askfm.impl;

import java.sql.SQLException;
import java.util.List;

import askfm.api.Entity;
import askfm.api.EntityService;
import askfm.api.GeneralException;
import askfm.api.NoEntityException;
import askfm.api.NotValidEntityException;
import askfm.api.ServiceException;

public abstract class AbstractServiceImpl<I extends E, E extends Entity> implements EntityService<E> {
	protected final AbstractDAO<I, E> dao;
	
	protected abstract String getEntityName();
	
	protected abstract void checkEntity(E entity) throws NotValidEntityException;
	
	public AbstractServiceImpl(AbstractDAO<I, E> dao) throws ServiceException {
		this.dao = dao;
		initialPrepareData();
	}

	@Override
	public E createEntity(E entity) throws ServiceException, NotValidEntityException {
		try {
			checkEntity(entity);
			return dao.createIfNotExists(entity);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GeneralException(e);
		}
	}

	@Override
	public E getEntityById(Integer id) throws ServiceException, NoEntityException {
		try {
			E entity = dao.queryForId(id);
			if (entity == null) throw new NoEntityException(getEntityName(), id);
			return entity;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GeneralException(e);
		}

	}

	@Override
	public void initialPrepareData() throws ServiceException {
		try {
			dao.initialPrepareData();
		} catch (SQLException ex) {
			throw new GeneralException(ex);
		}
	}

	@Override
	public List<? extends E> getAll() throws ServiceException {
		try {
			return dao.getAll();
		} catch (SQLException ex) {
			throw new GeneralException(ex);
		}
	}
}