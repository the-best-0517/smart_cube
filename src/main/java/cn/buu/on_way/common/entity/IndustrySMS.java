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
	private static String to = "18701198791";
	private static String smsContent = "【智立方】尊敬的用户，您的验证码为{1}";

	/**
	 * 验证码通知短信
	 */
	public static void execute()
	{
		String tmpSmsContent = null;
	    try{
	      tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
	    //tmpSmsContent = smsContent;
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
