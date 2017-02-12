package askfm.impl.properties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import askfm.api.properties.DatabaseProperties;
import askfm.api.properties.PropertyService;

@Singleton
public class DatabasePropertiesImpl implements DatabaseProperties {
	public static final String SERVER_DATABASE_PROPERTY = "askfm.database";
	public static final String DATABASE_DRIVER_PROPERTY = "askfm.database.driver";

	final private String databaseURI;
	final private String driverClassName;

	@Inject
	public DatabasePropertiesImpl(PropertyService propertyService) {
		this(propertyService.getPropertyValue(SERVER_DATABASE_PROPERTY),
				propertyService.getPropertyValue(DATABASE_DRIVER_PROPERTY));
	}

	public DatabasePropertiesImpl(String databaseURI, String driverClassName) {
		this.databaseURI = databaseURI;
		this.driverClassName = driverClassName;
	}

	@Override
	public String getDatabaseURI() {
		return databaseURI;
	}

	@Override
	public String getDriverClassName() {
		return driverClassName;
	}

}
