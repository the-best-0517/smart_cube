package cn.buu.on_way.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.buu.on_way.common.entity.IndustrySMS;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/msg")
public class MesController extends CommonController{
	/**
	 * 发送验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/sendMsg")
	public JsonResult sendMsg(String phone) {
		System.out.println("esendMag");
		hanldDiff();
		IndustrySMS.to = phone;
		IndustrySMS.execute();
		return new JsonResult();
		
	}
	/**
	 * 检查验证码是否正确
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping("/checkCode")
	public JsonResult checkCode(String phone,String code) {
		System.out.println("checkCode");
		hanldDiff();
		if(Integer.parseInt(code)==IndustrySMS.code) {
			System.out.println("验证码输入正确");
			return new JsonResult();
		}else {
			return new JsonResult("验证码错误");
		}
		
		
	}
}
