package cn.buu.smart_cube.login.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.entity.AppPush;
import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;
import cn.buu.smart_cube.common.web.JsonResult;
import cn.buu.smart_cube.login.entity.User;

@Controller
@RequestMapping("/login")
public class LoginController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonServiceImpl commonServiceImpl;
	
	
	
	
	
	
	@RequestMapping("/pull")
	@ResponseBody
	public JsonResult pull() throws IOException {
		System.out.println("pull");
		hanldDiff();        
		AppPush app = new AppPush();
		app.appPush();
		System.out.println("pushok");
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("test/QryMsg");
		List<Map<String, Object>> data = exchangeDbService.selectDb(db);
		System.out.println("data:"+data);
		return new JsonResult(data);
		
	}
	/**
	 * ��ѯ�����û���
	 * @return
	 */
	@RequestMapping("/logon")
	@ResponseBody
	public JsonResult logon() {
		System.out.println("findAllUserNAme");
		hanldDiff();         //��������������⣨�̳�CommController��
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
		long userId = commonServiceImpl.getOnlyKey();  //������ʱ����+��λ�����
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
			//��ɫ��
			db.setSqlPath("login/saveUsertoRole");
			exchangeDbService.saveDb(db);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new JsonResult();		
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult login(String name,String pwd,HttpSession session) {
		System.out.println("login");
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
			/**ͨ��userName��ѯuserId*/
			lsc.setSqlPath("login/QryUserIdByUserName");
			List<Map<String,Object>> list1 = exchangeDbService.selectDb(lsc);
			if(list1.size()>0) {
				Object userId = list1.get(0).get("userId");
				session.setAttribute("userId", userId);//�󶨵�session��
			}
			return new JsonResult();
		}else {
			return new JsonResult("error");
		}
	}
	
	/**
	 * 查询个人基本信息
	 * @return
	 */
	@RequestMapping("/showPersonMsg")
	@ResponseBody
	public JsonResult showPersonMsg(String userId) {
		System.out.println("showPersonMsg");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userId",userId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("login/QryPersonMsgByUserId");
		lsc.setData(data);
		try {
			list = exchangeDbService.selectDb(lsc);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new JsonResult(list);
	}
	
	@RequestMapping("/savePersonMsg")
	@ResponseBody
	public JsonResult savePersonMsg(User user,HttpSession session) {
		System.out.println("savePersonMsg");
		hanldDiff();
		Object userId = session.getAttribute("userId");
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userName",user.getUserName());
		data.put("phone",user.getPhone());
		data.put("breakfast",user.getBreakfast());
		data.put("lunch",user.getLunch());
		data.put("dinner",user.getDinner());
		data.put("userId",userId);
		LscExchangeDb lsc  = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("login/updatePersonMsg");
		try {
			exchangeDbService.saveDb(lsc);
			return new JsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult(e);
		}
		
	}
	 @RequestMapping("/testaa")
	 @ResponseBody 
	 public JsonResult pictureUpload (String imageBase64,HttpSession session) {
	  hanldDiff();
	  System.out.println("pictureUpload");
	  System.out.println("imageBase64:"+imageBase64);
	  imageBase64 = imageBase64.replaceAll("data:image/png;base64,", "");
	  System.out.println("imageBase640.0:"+imageBase64);
	  try {
		  Date date = new Date();
		  SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		  String userId =session.getAttribute("userId").toString();
		  int s =(int)(Math.random()*99+1);
		  String path = "root"+File.separator+userId+File.separator+sdf.format(date)+s;
		  System.out.println("path:"+path);
		  commonServiceImpl.generateImage(imageBase64, path); 
		  return new JsonResult();
	  }catch(Exception e) {
		  e.printStackTrace();
		  return new JsonResult("error");
	  }
	 }
}
