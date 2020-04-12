package cn.zhucongqi.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
class OAuthClientApplicationTests {

	@Test
	void base64() {

		String clientId = "client-id";
		String clientSecret = "client-secret";

		byte[] encode = Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes());
		String base64 = new String(encode);
		System.out.println(base64);

	}

}
