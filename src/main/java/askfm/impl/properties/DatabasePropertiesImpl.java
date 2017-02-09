package askfm.impl.properties;

import com.google.inject.Inject;

import askfm.api.properties.DatabaseProperties;
import askfm.api.properties.PropertyService;

public class DatabasePropertiesImpl implements DatabaseProperties {
	private static final String SERVER_DATABASE_PROPERTY = "askfm.database";
	private static final String DATABASE_DRIVER_PROPERTY = "askfm.database.driver";

	final private String databaseURI;
	final private String driverClassName;

	@Inject
	public DatabasePropertiesImpl(PropertyService propertyService) {
		this.databaseURI = propertyService.getPropertyValue(SERVER_DATABASE_PROPERTY);
		this.driverClassName = propertyService.getPropertyValue(DATABASE_DRIVER_PROPERTY);
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
