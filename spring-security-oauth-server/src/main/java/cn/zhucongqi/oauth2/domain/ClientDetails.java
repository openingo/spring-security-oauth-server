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
public class ClientDetails extends Model<ClientDetails> {

    private String clientId;

    private String clientSecret;

    private String scope;

    private String webServerRedirectUri;

    private String authorities;

    private String grantTypes;
}
