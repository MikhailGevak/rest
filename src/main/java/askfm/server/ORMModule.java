package askfm.server;

import java.sql.SQLException;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import askfm.api.entity.question.QuestionService;
import askfm.api.ip.IpService;
import askfm.api.properties.DatabaseProperties;
import askfm.impl.entity.question.QuestionDAO;
import askfm.impl.entity.question.QuestionServiceImpl;
import askfm.impl.ip.IpServiceImpl;
import askfm.impl.ip.WebClient;
import askfm.impl.ip.WebClientImpl;

public class ORMModule implements Module {
	private DatabaseProperties databaseProperties;

	public ORMModule(DatabaseProperties databaseProperties) {
		this.databaseProperties = databaseProperties;
	}

	@Override
	public void configure(Binder binder) {
		try {
			binder.bind(ConnectionSource.class).toInstance(
					createConnectionSource());
			binder.bind(QuestionService.class).to(QuestionServiceImpl.class);
			binder.bind(QuestionDAO.class);
			binder.bind(WebClient.class).to(WebClientImpl.class);
			binder.bind(IpService.class).to(IpServiceImpl.class);
			
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	private ConnectionSource createConnectionSource() throws SQLException,
			ClassNotFoundException {
		Class.forName(org.sqlite.JDBC.class.getName());
		return new JdbcConnectionSource(databaseProperties.getDatabaseURI());
	}

}
