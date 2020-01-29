package cn.buu.smart_cube.login.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

import cn.buu.on_way.common.entity.AffMarkSMS;
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
	 *鑾峰彇褰撳墠鐧诲綍浜虹殑瑙掕壊
	 * @return
	 */
	@RequestMapping("/checkRole")
	@ResponseBody
	public JsonResult checkRole(HttpSession session) {
		System.out.println("checkRole");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		Object userId = session.getAttribute("userId");
		System.out.println("userId:"+userId);
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
	 * 妫�娴嬫鎵嬫満鍙锋槸鍚︽敞鍐岃繃
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
	 * 鏌ヨbuleMac 杩炴帴钃濈墮鐢�
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
	 * 鏌ヨ鎻愰啋鏃堕棿
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
        //+1浠婂ぉ鐨勬椂闂村姞涓�澶�
        calendar.add(Calendar.MINUTE,-10);
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
				//鐭俊瀹朵汉
				System.out.println("鐭俊瀹朵汉...");
				
				Map<String,Object> data = new HashMap<String,Object>();
				LscExchangeDb lsc = new LscExchangeDb();
				lsc.setSqlPath("remiand/QryFamilyUserId");
				List<Map<String,Object>> fuList = exchangeDbService.selectDb(lsc);
				for(int i=0;i<fuList.size();i++) {
				//	 AffMarkSMS.to=fuList.get(i).get("phone").toString();
				//	 AffMarkSMS.execute();
					data.put("fuId",fuList.get(i).get("userId"));
					data.put("informDesc","鎮ㄧ殑瀹跺睘宸茶秴杩囧悆鑽彁閱掓椂闂�10鍒嗛挓锛岃鍏虫敞锛�");
					lsc.setData(data);
					lsc.setSqlPath("remiand/makeNotic");
					exchangeDbService.saveDb(lsc);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		db.setSqlPath("remiand/QryIfRemaind");
		List<Map<String, Object>> data = exchangeDbService.selectDb(db);
		List<String> list = new ArrayList<String>();
		System.out.println("data:"+data);
		if(data!=null&data.size()>0) {
			System.out.println("涓嶆槸绌�");
			int bNum = Integer.parseInt(data.get(0).get("boxId").toString());
			/**鏌ヨ鑷繁鐨勮嵂鐩掓暟*/
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
			
			System.out.println("鍐欓�氱煡锛氳鍚冭嵂浜�");
			Map<String,Object> d = new HashMap<String,Object>();
			d.put("informDesc","璇ュ悆鑽簡");
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
	 * 锟斤拷询锟斤拷锟斤拷锟矫伙拷锟斤拷
	 * @return
	 */
	@RequestMapping("/logon")
	@ResponseBody
	public JsonResult logon() {
		System.out.println("findAllUserNAme");
		hanldDiff();         //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷猓拷坛锟紺ommController锟斤拷
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
		System.out.println("email:"+email);
		if(!code.equals(Integer.parseInt(email))) {
			System.out.println("楠岃瘉鐮侀敊璇�");
			return new JsonResult("楠岃瘉鐮侀敊璇�");
		}
		long userId = commonServiceImpl.getOnlyKey(); 
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userName", userName);
		data.put("userId", userId);
		data.put("pwd",pwd);
		data.put("phone",phone);
		data.put("roleId","normal");
		data.put("headImgPath", "file/headImg/icon.png");
		data.put("qrCode", "file/codeImg/123.png");
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("login/saveUser");
		db.setData(data);
		
		try {
			exchangeDbService.saveDb(db);
			//淇濆瓨瑙掕壊淇℃伅
			db.setSqlPath("login/saveUsertoRole");
			exchangeDbService.saveDb(db);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			IndustrySMS.code=(int)((Math.random()*9+1)*100000);
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
			/**通锟斤拷userName锟斤拷询userId*/
			lsc.setSqlPath("login/QryUserIdByUserName");
			List<Map<String,Object>> list1 = exchangeDbService.selectDb(lsc);
			System.out.println("list1:"+list1);
			if(list1.size()>0) {
				Object userId = list1.get(0).get("userId");
				session.setAttribute("userId", userId);//锟襟定碉拷session锟斤拷
			}
			return new JsonResult();
		}else {
			return new JsonResult("error");
		}
	}
	
	/**
	 * 鏌ヨ涓汉鍩烘湰淇℃伅
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
		  commonServiceImpl.generateImage(imageBase64, path);  //瑙ｅ瘑骞朵繚瀛樺浘鐗�
		  return new JsonResult();
	  }catch(Exception e) {
		  e.printStackTrace();
		  return new JsonResult("error");
	  }
	 }
	 
	 
	 

		static String JCO_CLIENT_LANG;
		static String JCO_CLIENT_CLIENT;
		static String JCO_CLIENT_PASSWD;
		static String JCO_CLIENT_USER;
		static String JCO_CLIENT_SYSNR;
		static String JCO_CLIENT_ASHOST;
		static String JCO_CLIENT_SAPROUTER;
		@RequestMapping("/file")
		public String index() throws IOException {
			System.out.println("index");
		//	return "redirect:index.html";
			String cs = this.getClass().getClassLoader().getResource("") + "system.properties";
			System.out.println("cs:"+cs);
		//	System.out.println("cslength:"+cs.substridng(5,cs.length()));
		//	String classPath=
		//	"/www/server/apache-tomcat-default/webapps/smart_cube/WEB-INF/classes/system.properties"
	//;
			readConfig(cs.substring(6,cs.length()));
			System.out.println("lsc:"+JCO_CLIENT_LANG);
			return "hello world";
			


			
		}
		public void readConfig(String classPath) throws IOException {
			System.out.println(classPath);
			// File file = new File("C:/Users/lsc/Desktop/system.properties");
			File file = new File(classPath);
			FileInputStream f = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				f = new FileInputStream(file);
				isr = new InputStreamReader(f);
				br = new BufferedReader(isr);
				String str = null;
				while ((str = br.readLine()) != null) {
					System.out.println(str);
					if (str.startsWith("JCO")) {
						makesap(str);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				f.close();
				isr.close();
				br.close();
			}
		}
		private void makesap(String str) {
			if (str.startsWith("JCO.CLIENT.LANG")) {
				JCO_CLIENT_LANG = str.substring(str.indexOf("=") + 1);
			} else if (str.startsWith("JCO.CLIENT.CLIENT")) {
				JCO_CLIENT_CLIENT = str.substring(str.indexOf("=") + 1);
			} else if (str.startsWith("JCO.CLIENT.PASSWD")) {
				JCO_CLIENT_PASSWD = str.substring(str.indexOf("=") + 1);
			} else if (str.startsWith("JCO.CLIENT.USER")) {
				JCO_CLIENT_USER = str.substring(str.indexOf("=") + 1);
			} else if (str.startsWith("JCO.CLIENT.SYSNR")) {
				JCO_CLIENT_SYSNR = str.substring(str.indexOf("=") + 1);
			} else if (str.startsWith("JCO.CLIENT.ASHOST")) {
				JCO_CLIENT_ASHOST = str.substring(str.indexOf("=") + 1);
			} else if (str.startsWith("JCO.CLIENT.SAPROUTER")) {
				JCO_CLIENT_SAPROUTER = str.substring(str.indexOf("=") + 1);
			}
		}

}
