package askfm.api.exceptions;

import org.apache.commons.lang3.StringUtils;

import askfm.api.ServiceException;

public class NotValidEntityException extends ServiceException {
	public static Integer DEFAULT_CODE = 300;
	
	public NotValidEntityException(String reason, String entityName) {
		super(entityName + " is not valid" + (StringUtils.isNotEmpty(reason) ? (" (" + reason + ")") : ""), DEFAULT_CODE);
	}

	public NotValidEntityException(String entityName) {
		this("", entityName);
	}

	private static final long serialVersionUID = 1L;
}
