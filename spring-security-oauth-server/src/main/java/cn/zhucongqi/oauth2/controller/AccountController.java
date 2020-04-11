package cn.zhucongqi.oauth2.controller;

import cn.zhucongqi.oauth2.domain.Account;
import cn.zhucongqi.oauth2.domain.RespData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

/**
 * 账户控制器
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/register/{username}/{password}")
    public RespData register(@PathVariable("username") String username,
                             @PathVariable("password") String password) {
        Account account = new Account();
        account.setUsername(username);
        // 将 clientSecret 进行 BCrpt 处理
        account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        boolean ret = account.insert();
        return RespData.respData(ret);
    }

}
