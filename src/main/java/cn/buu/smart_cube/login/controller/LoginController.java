package cn.buu.smart_cube.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/login")
public class LoginController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	
	/**
	 * 查询所有用户名
	 * @return
	 */
	@RequestMapping("/logon")
	@ResponseBody
	public JsonResult logon() {
		System.out.println("findAllUserNAme");
		hanldDiff();
		Map<String,Object> map = new HashMap<String,Object>();
		LscExchangeDb db = new LscExchangeDb();
		db.setData(map);
		db.setSqlPath("login/QryAllUserName");
		List<Map<String, Object>> data = exchangeDbService.selectDb(db);
		System.out.println("data:"+data);
		return new JsonResult(data);
		
	}
}
