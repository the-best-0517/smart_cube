package cn.buu.on_way.common.entity;

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// 获取开发者账号信息
		// AccountInfo.execute();

		// 验证码通知短信接口
//		IndustrySMS.to="18701198791";
//	 IndustrySMS.execute();

		// 会员营销短信接口
		AffMarkSMS.to="18701198791";
		 AffMarkSMS.execute();

		// 语音验证码
		// VoiceCode.execute();

	}
}
