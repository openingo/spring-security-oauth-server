package cn.zhucongqi.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OAuth2 配置
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 密码编码方式
     */
    @Autowired
    public PasswordEncoder passwordEncoder;

    /**
     * 用户信息处理
     */
    @Autowired
    public UserDetailsService userDetailsService;

    /**
     * 授权管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * token存储
     */
    @Autowired
    private TokenStore jdbcTokenStore;

    @Autowired
    private TokenEnhancer jdbcTokenEnhancer;

    @Autowired
    private JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // token 增强器链
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(this.jdbcTokenEnhancer);
        enhancerChain.setTokenEnhancers(enhancerList);
        // 配置 token 增强链
        endpoints.tokenEnhancer(enhancerChain)
            // 指定token的存储形式
            .tokenStore(this.jdbcTokenStore)
            // 指定 userDetail的处理方式
            .userDetailsService(this.userDetailsService)
            // 指定AuthorizationCode的处理方式
            .authorizationCodeServices(this.jdbcAuthorizationCodeServices)
            // 指定授权处理器
            .authenticationManager(this.authenticationManager);

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
    }

    /**
     * Client-id & Client-Secret & Scope信息校验
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * Mapping 接口
     * <p>
     * POST /oauth/authorize 授权码模式认证授权接口
     * GET/POST /oauth/token_key 获取 token 的接口
     * POST /oauth/check_token 检查 token 合法性接口
     * </p>
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.tokenKeyAccess("permitAll()");
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }
}
