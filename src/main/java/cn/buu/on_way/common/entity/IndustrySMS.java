package cn.buu.on_way.common.entity;

import java.net.URLEncoder;

import cn.buu.on_way.common.entity.common.Config;
import cn.buu.on_way.common.entity.common.HttpUtil;



/**
 * 验证码通知短信接口
 * 
 * @ClassName: IndustrySMS
 * @Description: 验证码通知短信接口
 *
 */
public class IndustrySMS
{
	private static String operation = "/industrySMS/sendSMS";

	private static String accountSid = Config.ACCOUNT_SID;
	public static String to = "";
	public static int code = (int)((Math.random()*9+1)*100000);
	private static String smsContent = "【智立方】您的验证码："+code+"，如非本人操作，请忽略此短信。";
	//private static String smsContent2 = "【智立方】您的家属距离服药时间超过30分钟还未服药，请关注";

	/**
	 * 验证码通知短信
	 */
	public static void execute()
		
	{
		String tmpSmsContent = null;
	    try{
	     // tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
	    tmpSmsContent = smsContent;
	    }catch(Exception e){
	      
	    }
	    String url = Config.BASE_URL + operation;
	    String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
	        + HttpUtil.createCommonParam();

	    // 提交请求
	    String result = HttpUtil.post(url, body);
	    System.out.println("result:" + System.lineSeparator() + result);
	}
	
}
