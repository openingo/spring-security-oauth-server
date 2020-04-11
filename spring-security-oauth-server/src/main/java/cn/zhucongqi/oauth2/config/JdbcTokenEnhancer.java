package cn.zhucongqi.oauth2.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Token 增强器便于加入扩展信息
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
public class JdbcTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 测试的扩展信息
        Map<String, Object> info = new HashMap<>();
        info.put("uid", UUID.randomUUID());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
