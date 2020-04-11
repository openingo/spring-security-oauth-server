package cn.zhucongqi.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 测试入口
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Controller
public class IndexController {

    @GetMapping(value = "index")
    public String index(){
        return "redirect:http://localhost:8080/oauth/authorize?client_id=client-id&response_type=code";
    }
}
