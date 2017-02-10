package askfm.api.ip;

import askfm.api.ServiceException;

public interface IpService {
	IPinfo getInfoByIp(String ip) throws ServiceException;
}
