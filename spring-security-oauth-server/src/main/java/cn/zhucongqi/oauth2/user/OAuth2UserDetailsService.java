package cn.zhucongqi.oauth2.user;

import cn.zhucongqi.oauth2.dao.AccountDao;
import cn.zhucongqi.oauth2.domain.Account;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息合法性校验
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Component(value = "userDetailsService")
public class OAuth2UserDetailsService implements UserDetailsService {

    /**
     * 密码编码器，使用构造方法注入
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * 账户 Mapper，使用构造方法注入
     */
    private final AccountDao accountDao;

    public OAuth2UserDetailsService(PasswordEncoder passwordEncoder,
                                    AccountDao accountDao) {
        this.passwordEncoder = passwordEncoder;
        this.accountDao = accountDao;
    }

    /**
     * 在数据库中查找对应用户是否存在
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查找是否存在合法用户
        LambdaQueryWrapper<Account> userLambdaQueryWrapper = Wrappers.<Account>lambdaQuery();
        userLambdaQueryWrapper.eq(Account::getUsername,username);
        Account account = this.accountDao.selectOne(userLambdaQueryWrapper);
        if (null == account) {
            // 如何没有找到 account，自己返回null，由Spring Security OAuth2内部处理
            return null;
        }

        // TODO 权限处理
        String authority = "ROLE_ADMIN";
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(authority));
        return (new User(username, account.getPassword(), authorities));
    }
}
