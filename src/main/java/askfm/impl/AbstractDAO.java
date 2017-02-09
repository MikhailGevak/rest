package askfm.impl;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import askfm.api.Entity;

public abstract class AbstractDAO<I extends E, E extends Entity> extends BaseDaoImpl<I, Integer> {
	protected AbstractDAO(ConnectionSource connectionSource, Class<I> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}

	public List<? extends E> getAll() throws SQLException {
		return this.queryForAll();
	}

	public abstract void initialPrepareData() throws SQLException;
	
	protected abstract I toImpl(E data);
	
	public E createIfNotExists(E data) throws SQLException{
		return super.createIfNotExists(toImpl(data));
	}
}
