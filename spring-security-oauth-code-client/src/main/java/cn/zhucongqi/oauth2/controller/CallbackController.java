package cn.zhucongqi.oauth2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * authorization_code 回调服务
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@RestController
public class CallbackController {

    @GetMapping(value = "callback")
    public Object callback(String code) {
        Map resp = new HashMap();
        if (null == code) {
            resp.put("description", "获取code失败");
            return resp;
        }
        String tokenUrl = "http://localhost:8080/oauth/token";
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client", "client-id")
            // 仅用于校验，不会再次redirect
                .add("redirect_uri","http://localhost:18080/callback")
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(body)
            //Authorization = "Basic Base64(client-id:client-secret)"
                .addHeader("Authorization", "Basic Y2xpZW50LWlkOmNsaWVudC1zZWNyZXQ=")
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            String result = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            Map tokenData = objectMapper.readValue(result, Map.class);
            Object accessToken = tokenData.get("access_token");
            if (null == accessToken) {
                return tokenData;
            }
            tokenData.put("self-property", "这是自定义属性");
            return tokenData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
