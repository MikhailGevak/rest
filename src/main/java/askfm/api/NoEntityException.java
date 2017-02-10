package askfm.api;

public class NoEntityException extends ServiceException {
	public static Integer DEFAULT_CODE = 200;
	
	public NoEntityException(String entityName, Integer id, Exception ex) {
		super("No " + entityName + " with ID: " + id, DEFAULT_CODE, ex);
	}
	
	
	public NoEntityException(String entityName, Integer id) {
		super("No " + entityName + " with ID: " + id, DEFAULT_CODE);
	}

	private static final long serialVersionUID = 1L; 
}
