package cn.buu.smart_cube.setting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.impl.ExchangeDbServiceImpl;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/setting")
public class PowerController extends CommonController{
	
	@Resource
	private ExchangeDbServiceImpl exchangeDbServiceImpl;
	@Resource
	private CommonServiceImpl commonServiceImpl;
	
	@RequestMapping("/updatePower")
	@ResponseBody
	public JsonResult updatePower(String userId,String powerId) {
		System.out.println("updatePower");
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userId",userId);
		data.put("roleId",powerId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("setting/updateRoleByUserId");
		return new JsonResult();	
	}
	
	
	@RequestMapping("/showAllUser")
	@ResponseBody
	public JsonResult showAllUser() {
		System.out.println("showAllUser");
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("setting/QryAllUser");
		try {
			List<Map<String,Object>> list = exchangeDbServiceImpl.selectDbNoParam(lsc);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
}
