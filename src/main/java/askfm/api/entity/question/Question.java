package askfm.api.entity.question;

import java.util.Date;

import askfm.api.entity.Entity;

public interface Question extends Entity {
	String getText();
	String getCountry();
	Date getDateTime();	
}
