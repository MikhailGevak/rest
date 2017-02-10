package askfm.rest;

import askfm.api.ServiceException;

@FunctionalInterface
interface CreateResponse<T> {
	public T get() throws ServiceException;
}
