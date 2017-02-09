package askfm.test.bind;

import com.google.inject.Binder;
import com.google.inject.Module;

import askfm.impl.ip.WebClient;
import askfm.impl.ip.WebClientMock;

public class ThirdPartyModule implements Module {

	@Override
	public void configure(Binder binder) {
		binder.bind(WebClient.class).to(WebClientMock.class);
	}
}
