package cn.buu.on_way.common.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppPush {

    //���峣��, appId��appKey��masterSecret ���ñ��ĵ� "�ڶ��� ��ȡ����ƾ֤ "�л�õ�Ӧ������
    private static String appId = "iMQV2IxFa2AA7N3DwcgVZ5";
    private static String appKey = "j6ZS3gwXQrALiUUS5ru6FA";
    private static String masterSecret = "84rPHaFuRC69m8G4l1XZB4";
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void main(String[] args) throws IOException {

        IGtPush push = new IGtPush(url, appKey, masterSecret);

        // ����"������Ӵ�֪ͨģ��"�������ñ��⡢���ݡ�����
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTitle("����д֪ͨ����");
        template.setText("����д֪ͨ����");
        template.setUrl("http://getui.com");

        List<String> appIds = new ArrayList<String>();
        appIds.add(appId);

        // ����"AppMessage"������Ϣ����������Ϣ����ģ�塢���͵�Ŀ��App�б��Ƿ�֧�����߷��͡��Լ�������Ϣ��Ч��(��λ����)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
    }
}


