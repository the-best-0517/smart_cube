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
import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/login")
public class LoginController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonServiceImpl commonServiceImpl;
	/**
	 * 查询所有用户名
	 * @return
	 */
	@RequestMapping("/logon")
	@ResponseBody
	public JsonResult logon() {
		System.out.println("findAllUserNAme");
		hanldDiff();         //处理跨域请求问题（继承CommController）
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("login/QryAllUserName");
		List<Map<String, Object>> data = exchangeDbService.selectDb(db);
		System.out.println("data:"+data);
		return new JsonResult(data);
		
	}
	
	@RequestMapping("/saveUser")
	@ResponseBody
	public JsonResult savaUser(String userName,String pwd,String phone) {
		System.out.println("savaUser");
		hanldDiff();
		long userId = commonServiceImpl.getOnlyKey();  //年月日时分秒+五位随机数
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userName", userName);
		data.put("userId", userId);
		data.put("pwd",pwd);
		data.put("phone",phone);
		data.put("roleId","normal");
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("login/saveUser");
		db.setData(data);
		
		try {
			int rows = exchangeDbService.saveDb(db);
			//角色表
			db.setSqlPath("login/saveUsertoRole");
			exchangeDbService.saveDb(db);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new JsonResult();		
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult login(String name,String pwd) {
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userName",name);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("login/checkPwdByUserName");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		if(list.size()==0) {
			return new JsonResult("error");
		}
		Object truepwd = list.get(0).get("pwd");
		if(pwd.equals(truepwd)) {
			return new JsonResult();
		}else {
			return new JsonResult("error");
		}
	}
}
