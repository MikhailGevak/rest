package askfm.api;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceException(Exception ex) {
		super(ex);
	}
}
