## Spring Security OAuth Server

> 基于Spring Boot & Spring Cloud OAuth2 & MyBatis-plus

#### 数据库：

```sql
-- 用户校验账户信息
CREATE TABLE `oauth_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- 用户校验client信息
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` varchar(128) DEFAULT NULL,
  `client_secret` varchar(128) DEFAULT NULL,
  `scope` varchar(128) DEFAULT NULL,
  `authorized_grant_types` varchar(128) DEFAULT NULL,
  `web_server_redirect_uri` varchar(128) DEFAULT NULL,
  `authorities` varchar(128) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- token存储
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(128) DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication_id` varchar(128) NOT NULL,
  `user_name` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `authentication` blob DEFAULT NULL,
  `refresh_token` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- code 存储，一次性存储使用完成之后，就删除
CREATE TABLE `oauth_code` (
  `code` varchar(128) DEFAULT NULL,
  `authentication` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- refresh token 存储
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(128) DEFAULT NULL,
  `token` blob DEFAULT NULL,
  `authentication` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


#### 密码方式：

```html
http://localhost:8080/oauth/token?grant_type=password&username={username}&password={password}&scope={scope}
```

> 测试信息：username=zcq，password=zcq，scope=ios
>
> `Header`中`Authorization: Basic Y2xpZW50LWlkOmNsaWVudC1zZWNyZXQ=`
>
> > Base64('client-id:client-secret')



#### Code方式：

```
http://localhost:18080/index
```

> 实际请求为:
>
> ```
> http://localhost:8080/oauth/authorize?client_id=client-id&response_type=code
> ```

> redirect_uri的处理，spring security oauth2，真正的跳转用的是db里面的redicet_uri，请求中的uri仅用于内存校验。
>
> > 注意这个回调，成功失败都均会执行。

#### 资源访问

获得`access_token`后，获取资源时在`header`中加入`Authorization:bearer access_token`即可。



#### 刷新Token

```
http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token={refresh_token}
```

> `refresh_token`为上一次请求返回的`refresh_token`;
>
> `Header`中`Authorization: Basic Y2xpZW50LWlkOmNsaWVudC1zZWNyZXQ=`



#### 配置Token有效期

在`cn.zhucongqi.oauth2.config.OAuth2Config#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)`中加入如下配置：

```java
 // token 相关配置
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        // 设置之后，过期时间配置无效
        // tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 30天
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));

        endpoints.tokenServices(tokenServices);
```



#### 自定义属性

> 使用`TokenEnhancer`

##### 自定义`access_token`和`refresh_token`

```java
public class JdbcTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 扩展信息
        Map<String, Object> info = new HashMap<>();
        info.put("uid", UUID.randomUUID());

        DefaultOAuth2AccessToken at = (DefaultOAuth2AccessToken) accessToken;

        // 配置扩展信息
        at.setAdditionalInformation(info);
        // 修改access_token值
        at.setValue("prefix"+UUID.randomUUID().toString());
        // 修改refresh_tokens
        DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken("refresh_token:"+UUID.randomUUID());
        at.setRefreshToken(refreshToken);
        
        return accessToken;
    }
}
```



#### TODO

- 自定义登录界面
- 高可用