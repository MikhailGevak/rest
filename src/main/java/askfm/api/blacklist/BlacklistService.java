package askfm.api.blacklist;

public interface BlacklistService {
	/*
	 * Return blank string ("") if text is valid or the first world in the text which was find in black list
	 */
	String isValid(String text);
}
