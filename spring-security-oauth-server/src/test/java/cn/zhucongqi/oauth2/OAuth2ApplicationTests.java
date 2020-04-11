package cn.zhucongqi.oauth2;

import cn.zhucongqi.oauth2.domain.ClientDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootTest
class OAuth2ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void geBCryptCode() {
		String hashpw = BCrypt.hashpw("client-secret", BCrypt.gensalt());
	}

	@Test
	void createClient() {
		String clientId = "";
		String clientSecret = "";
		String scope = "";
		String authorities = "";
		String redirectUri = "";
		String grantTypes = "authorization_code,refresh_token,password";

		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setClientId(clientId);
		clientDetails.setClientSecret(BCrypt.hashpw(clientSecret, BCrypt.gensalt()));
		clientDetails.setScope(scope);
		clientDetails.setAuthorities(authorities);
		clientDetails.setWebServerRedirectUri(redirectUri);
		clientDetails.setGrantTypes(grantTypes);

		boolean ret = clientDetails.insert();

	}

}
