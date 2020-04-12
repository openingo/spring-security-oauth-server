package cn.zhucongqi.oauth2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 *  客户端 Client & Client Secret信息
 *
 * @author zhucongqi
 * @date 2020/3/3
 */
@Component
public class AuthorizationClient {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
    /**
     * 获取 Header Authorization 信息
     * @return
     */
    public String getHeaderAuthorization() {
        StringBuilder authorizationBuilder = new StringBuilder("Basic ");

        //Authorization = "Basic Base64(client-id:client-secret)"
        StringBuilder clientBuilder = new StringBuilder();
        clientBuilder.append(AuthorizationClient.this.clientId);
        clientBuilder.append(":");
        clientBuilder.append(AuthorizationClient.this.clientSecret);

        byte[] encode = Base64.getEncoder().encode(clientBuilder.toString().getBytes());
        authorizationBuilder.append(new String(encode));

        return authorizationBuilder.toString();
    }

    @Bean
    public AuthorizationClient clientAuthorization() {
        return new AuthorizationClient();
    }
}
