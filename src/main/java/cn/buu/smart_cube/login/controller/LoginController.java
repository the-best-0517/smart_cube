package cn.buu.smart_cube.login.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.IndustrySMS;
import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;
import cn.buu.smart_cube.common.web.JsonResult;
import cn.buu.smart_cube.login.entity.User;
/**
 * 
 * @author ABC
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonServiceImpl commonServiceImpl;
	
	/**
	 *获取当前登录人的角色
	 * @return
	 */
	@RequestMapping("/checkRole")
	@ResponseBody
	public JsonResult checkRole(HttpSession session) {
		System.out.println("checkRole");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		Object userId = session.getAttribute("userId");
		data.put("userId", userId==null?123:userId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("login/QryRoleByNowUser");
		try {
			List<Map<String,Object>> roleList = exchangeDbService.selectDb(lsc);
			return new JsonResult(roleList);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}
		
	}
	
	/**
	 * 检测此手机号是否注册过
	 * @param phone
	 * @return
	 */
	@RequestMapping("/checkPhone")
	@ResponseBody
	public boolean checkPhone(String phone) {
		System.out.println("checkPhone");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("phone", phone);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("login/checkPhone");
		try {
			List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
			System.out.println("list:"+list);
			if(!list.isEmpty()&&list.size()>0) {
				return false;	
			}
			return true;	
		}catch(Exception e) {
			e.printStackTrace();
			return false;	
		}		
	}
	
	
	/**
	 * 查询buleMac 连接蓝牙用
	 * @param session
	 * @return
	 */
	@RequestMapping("/linkBuleTooth")
	@ResponseBody
	public JsonResult linkBuleTooth(HttpSession session) {
		System.out.println("linkBuleTooth");
		hanldDiff();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> data = new HashMap<String, Object>();
		Object userId = session.getAttribute("userId");
		data.put("userId", userId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("login/QryBuleToothMac");
		try {
			list = exchangeDbService.selectDb(lsc);
			if(list.size()>0) {
				return new JsonResult(list);
			}else {
				return new JsonResult("null");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}
	}
	
	/**
	 * 查询提醒时间
	 * @return
	 * @throws IOException
	 */	
	@RequestMapping("/pull")
	@ResponseBody
	public JsonResult pull(HttpSession session) throws IOException {
		System.out.println("pull");
		hanldDiff();        
		Date date = new Date();        
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowDate = sdf.format(date);
	 	Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //+1今天的时间加一天
        calendar.add(Calendar.MINUTE,-30);
        date = calendar.getTime();
        String before30 = sdf.format(date);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nowDate",nowDate);
		map.put("before", before30);
		Object userId = session.getAttribute("userId");
		map.put("userId", userId==null?123:userId);
		LscExchangeDb db = new LscExchangeDb();
		db.setData(map);
		try {
			db.setSqlPath("remiand/QryIfBeforeRemaind");
			List<Map<String, Object>> d = exchangeDbService.selectDb(db);
			if(d!=null&d.size()>0) {
				//短信家人
				System.out.println("短信家人...");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		db.setSqlPath("remiand/QryIfRemaind");
		List<Map<String, Object>> data = exchangeDbService.selectDb(db);
		List<String> list = new ArrayList<String>();
		System.out.println("data:"+data);
		if(data!=null&data.size()>0) {
			System.out.println("不是空");
			int bNum = Integer.parseInt(data.get(0).get("boxId").toString());
			/**查询自己的药盒数*/
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(map);
			lsc.setSqlPath("login/QryAllBoxMsg");
			List<Map<String,Object>> allBox = exchangeDbService.selectDb(lsc);
			int squareNumber = 0;
			String str = "";
			int k = 0;
			for(int i=0;i<allBox.size();i++) {
				k = squareNumber;
				squareNumber = squareNumber+Integer.parseInt(allBox.get(i).get("squareNumber").toString());
				System.out.println("squareNumber:"+squareNumber);
				if(bNum<=squareNumber) {
					int n = i+1;
					System.out.println("nn:"+n);
					str = "O0"+n;
					break;
				}
			}		
			for(k=k+1;k<=bNum;k++) {
				if(k==bNum) {
					str = str+"1";
					break;
				}
				str = str + "0";
			}		
			//O01100000000
			System.out.println("str:"+str);
			list.add(str);
			list.add(data.get(0).get("boxId").toString());
			
			System.out.println("写通知：该吃药了");
			Map<String,Object> d = new HashMap<String,Object>();
			d.put("informDesc","该吃药了");
			d.put("userId", userId==null?123:userId);
			lsc.setData(d);
			lsc.setSqlPath("login/makeNotic");
			exchangeDbService.saveDb(lsc);
			
			System.out.println("list:"+list);
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
	public JsonResult savaUser(String userName,String pwd,String phone,String email) {
		System.out.println("savaUser");
		hanldDiff();
		Integer code = IndustrySMS.code;
		System.out.println("code:"+code);
		if(!email.equals(code)) {
			return new JsonResult("验证码错误");
		}
		long userId = commonServiceImpl.getOnlyKey(); 
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userName", userName);
		data.put("userId", userId);
		data.put("pwd",pwd);
		data.put("phone",phone);
		data.put("roleId","normal");
		data.put("headImgPath", "file/headImg/icno.png");
		data.put("qrCode", "file/codeImg/123.png");
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("login/saveUser");
		db.setData(data);
		
		try {
			int rows = exchangeDbService.saveDb(db);
			//保存角色信息
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
