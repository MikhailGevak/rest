package askfm.api.exceptions;

import askfm.api.ServiceException;

public class ManyRequestException extends ServiceException {
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Too many requests. Try again soon.";

	public static Integer DEFAULT_CODE = 400;

	public ManyRequestException(Exception ex) {
		super(MESSAGE, DEFAULT_CODE, ex);
	}

	public ManyRequestException() {
		super(MESSAGE, DEFAULT_CODE);
	}
}
