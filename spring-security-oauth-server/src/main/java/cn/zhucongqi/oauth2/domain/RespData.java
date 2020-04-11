package cn.zhucongqi.oauth2.domain;

import lombok.Data;

/**
 * 请求响应数据
 *
 * @author zhucongqi
 * @date 2020/3/2
 */
@Data
public class RespData {

    private String ec;

    private String em;

    private String description;

    public static RespData respData(Boolean ret) {
        RespData respData = new RespData();
        respData.ec = "200";
        respData.em = "成功";
        respData.description = "注册成功";
        if (!ret) {
            respData.ec = "500";
            respData.em = "失败";
            respData.description = "注册失败";
        }
        return respData;
    }

}
