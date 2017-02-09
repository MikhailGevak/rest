package askfm.api.question;

import java.util.Date;

import askfm.api.Entity;

public interface Question extends Entity {
	String getText();
	String getCountry();
	Date getDateTime();	
}
