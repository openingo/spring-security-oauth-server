package cn.zhucongqi.oauth2.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * TODO
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Data
public class Account extends Model<Account> {

    private Long id;

    private String username;

    private String password;
}
