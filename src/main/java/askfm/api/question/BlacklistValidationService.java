package askfm.api.question;

import askfm.api.ValidationService;

public interface BlacklistValidationService extends ValidationService<Question, String>{
	/*
	 * Return blank string ("") if text is valid or the first world in the text which was find in black list
	 */
}
