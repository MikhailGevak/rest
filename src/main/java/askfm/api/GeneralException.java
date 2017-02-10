package askfm.api;

public class GeneralException extends ServiceException {
	public static Integer DEFAULT_CODE = 100;
	
	private static final long serialVersionUID = 1L;
	
	public GeneralException(String message){
		super(message, DEFAULT_CODE);
	}
			
	public GeneralException(String message, Exception ex) {
		super(message, DEFAULT_CODE, ex);
	}	
	

	public GeneralException(Exception ex) {
		super(DEFAULT_CODE, ex);
	}	
}
