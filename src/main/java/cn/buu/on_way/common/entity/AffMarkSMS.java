package cn.buu.on_way.common.entity;

import java.net.URLEncoder;

import cn.buu.on_way.common.entity.common.Config;
import cn.buu.on_way.common.entity.common.HttpUtil;



/**
 * 浼氬憳钀ラ攢鐭俊鎺ュ彛
 * 
 * @ClassName: AffMarkSMS
 * @Description: 浼氬憳钀ラ攢鐭俊鎺ュ彛
 *
 */
public class AffMarkSMS
{
	private static String operation = "/affMarkSMS/sendSMS";

	private static String accountSid = Config.ACCOUNT_SID;
	public static String to = "";
	private static String smsContent = "【智立方】您的家属在设定的时间内未进行任何操作，已逾期10分钟，请注意及时提醒~";
	
	/**
	 * 浼氬憳钀ラ攢鐭俊
	 */
	public static void execute()
	{
		String tmpSmsContent = null;
	    try{
	      //tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
	      tmpSmsContent = smsContent;
	    }catch(Exception e){
	      
	    }
	    String url = Config.BASE_URL + operation;
	    String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + tmpSmsContent
	        + HttpUtil.createCommonParam();

	    // 鎻愪氦璇锋眰
	    String result = HttpUtil.post(url, body);
	    System.out.println("result:" + System.lineSeparator() + result);
	}
}
