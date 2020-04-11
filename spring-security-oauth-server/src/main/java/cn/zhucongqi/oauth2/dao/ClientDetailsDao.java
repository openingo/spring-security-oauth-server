package cn.zhucongqi.oauth2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.zhucongqi.oauth2.domain.ClientDetails;
import org.springframework.stereotype.Component;

/**
 * ClientDetails Dao
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Component
public interface ClientDetailsDao extends BaseMapper<ClientDetails> {

}
