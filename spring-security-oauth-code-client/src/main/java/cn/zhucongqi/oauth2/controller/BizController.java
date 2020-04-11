package cn.zhucongqi.oauth2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TODO
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@RestController
public class BizController {

    /**
     * 获得`access_token`后，获取资源时在`header`中加入`Authorization:bearer access_token`即可。
     */
    @GetMapping("/show")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Map showData() {
        Map data = new HashMap();
        data.put("id", UUID.randomUUID());
        data.put("data", UUID.randomUUID());
        return data;
    }
}
