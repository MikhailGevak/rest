package askfm.server.bind;

import com.google.inject.Binder;
import com.google.inject.Module;

import askfm.impl.ip.WebClient;
import askfm.impl.ip.WebClientImpl;

public class ThirdPartyModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(WebClient.class).to(WebClientImpl.class);
	}
}
