package cn.buu.smart_cube.common.entity;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppPush {

    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String appId = "iMQV2IxFa2AA7N3DwcgVZ5";
    private static String appKey = "j6ZS3gwXQrALiUUS5ru6FA";
    private static String masterSecret = "84rPHaFuRC69m8G4l1XZB4";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public void appPush() throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);
       // IGtPush push = new IGtPush(appKey, masterSecret, false);

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("请填写通知标题");
        template.setText("请填写通知内容");
        template.setUrl("http://baidu.com");
        System.out.println("rem:"+template.toString());
        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
}
