package cn.zhucongqi.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * JDBC Token 配置
 *
 * @author zhucongqi
 * @date 2020/4/9
 */
@Configuration
public class JdbcStoreConfig {
    /**
     * 数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 使用DB方式存储token信息
     * @return jdbcTokenStore
     */
    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 使用DB方式存储AuthorizationCode信息
     * tips: code 在数据库中存储是一次性的，授权完成就删除了，也可以考虑仅存储在内存中
     * @return jdbcAuthorizationCodeServices
     */
    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * Token 增强器，用于加入一些扩展信息
     * @return
     */
    @Bean
    public TokenEnhancer jdbcTokenEnhancer() {
        return new JdbcTokenEnhancer();
    }
}
