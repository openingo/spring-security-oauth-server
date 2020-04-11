package cn.zhucongqi.oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.zhucongqi.oauth2.domain.Account;
import org.springframework.stereotype.Component;

/**
 * Account Dao
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Component // 解决idea的错误提示，其实可以不加
public interface AccountDao extends BaseMapper<Account> {

}
