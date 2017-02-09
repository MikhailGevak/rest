package askfm.server.bind;

import java.sql.SQLException;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import askfm.api.properties.DatabaseProperties;
import askfm.impl.question.QuestionDAO;

public class ORMModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(ConnectionSource.class).to(ConnectionSourceImpl.class);
		binder.bind(QuestionDAO.class);
	}

	public static class ConnectionSourceImpl extends JdbcConnectionSource {
		@Inject
		public ConnectionSourceImpl(DatabaseProperties databaseProperties) throws SQLException, ClassNotFoundException {
			super(databaseProperties.getDatabaseURI());
			Class.forName(databaseProperties.getDriverClassName());
		}
	}
}
