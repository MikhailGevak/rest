package askfm.server.bind;

import com.google.inject.Binder;
import com.google.inject.Module;

import askfm.api.blacklist.BlacklistService;
import askfm.api.ip.IpService;
import askfm.api.question.QuestionService;
import askfm.impl.blacklist.BlacklistServiceImpl;
import askfm.impl.ip.IpServiceImpl;
import askfm.impl.question.QuestionServiceImpl;

public class ServiceModule implements Module {
	@Override
	public void configure(Binder binder) {
		binder.bind(QuestionService.class).to(QuestionServiceImpl.class);
		binder.bind(IpService.class).to(IpServiceImpl.class);
		binder.bind(BlacklistService.class).to(BlacklistServiceImpl.class);
	}
}
