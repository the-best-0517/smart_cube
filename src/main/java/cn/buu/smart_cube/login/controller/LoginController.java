package cn.buu.smart_cube.login.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowDate = sdf.format(date);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nowDate",nowDate);
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("remiand/QryIfRemaind");
		db.setData(map);
		List<Map<String, Object>> data = exchangeDbService.selectDb(db);
		List<String> list = new ArrayList<String>();
		System.out.println("data:"+data);
		if(data!=null&data.size()>0) {
			System.out.println("不是空");
			//O01100000000
			list.add("O01100000000");
			return new JsonResult(list);
		}else {
			return new JsonResult("error");
		}				
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
	public JsonResult showPersonMsg(HttpSession session) {
		System.out.println("showPersonMsg");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		Object userId = session.getAttribute("userId");
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
		  int s =(int)(Math.random()*99+1);
		  String path = "/www/server/apache-tomcat-default/webapps/smart_cube/file/img/"+sdf.format(date)+s+".png";
		  System.out.println("path:"+path);
		  commonServiceImpl.generateImage(imageBase64, path);  //解密并保存图片
		  return new JsonResult();
	  }catch(Exception e) {
		  e.printStackTrace();
		  return new JsonResult("error");
	  }
	 }
}
