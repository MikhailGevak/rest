package askfm.api;

public abstract class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	private final Integer code;
	
	public Integer getCode(){
		return code;
	}
	
	public ServiceException(String message, Integer code){
		super(message);
		this.code = code;
	}
			
	public ServiceException(String message, Integer code, Exception ex) {
		super(message, ex);
		this.code = code;
	}	
	

	public ServiceException(Integer code, Exception ex) {
		super(ex);
		this.code = code;
	}	
}
