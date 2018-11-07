package cn.buu.smart_cube.mycase.controller;

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

@Controller
@RequestMapping("/case")
public class CaseController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonServiceImpl commonServiceImpl;
	
	@RequestMapping("/deleteImg")
	@ResponseBody
	public JsonResult deleteImg(String imgpath,String caseId) {
		System.out.println("deleteImg"+imgpath);
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>(16);
		System.out.println(imgpath);
		imgpath = imgpath.replace("http://120.79.10.49:8080/smart_cube/", "");
		System.out.println(imgpath);
		data.put("imgpath",imgpath);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("mycase/deleteImg");
		try {
			exchangeDbService.deleteDb(lsc);
			return new JsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
	
	
	/**
	 * 展示个人病例药品记录
	 * @param session
	 * @return
	 */
	@RequestMapping("/showBillRecord")
	@ResponseBody
	public JsonResult showBillRecord(HttpSession session,String caseId){
		System.out.println("showBillRecord");
		System.out.println("caseId:"+caseId);
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String,Object>(16);
		data.put("caseId", caseId);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("mycase/QryBillRecordByUserId");
		try {
			list = exchangeDbService.selectDb(lsc);
			System.out.println("lisy:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
	
	/**
	 * 删除病例用药记录
	 * @param caseId
	 * @param pillId
	 * @param session
	 * @return
	 */
	@RequestMapping("/deletePill")
	@ResponseBody
	public JsonResult deletePill(String caseId,String pillId,HttpSession session) {
		System.out.println("deletePill");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("caseId", caseId);
		data.put("pillId", pillId);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("mycase/deletePill");
		exchangeDbService.deleteDb(lsc);
		return new JsonResult();
		
	}
	
	/**
	 * 查询病例具体信息
	 */
	@RequestMapping("/showCaseDetail")
	@ResponseBody
	public JsonResult showCaseDetail(String caseId,HttpSession session) {
		System.out.println("showCaseDetail");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("caseId", caseId);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("mycase/QryCaseDetailByCaseId");
		try {
			List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
			System.out.println("list:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}
	}
	/**
	 * 删除病例
	 * @param caseId
	 * @return
	 */
	@RequestMapping("/delCase")
	@ResponseBody
	public JsonResult delCase(String caseId) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("caseId", caseId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("mycase/DeleteCaseByCaseId");
		try {
			exchangeDbService.deleteDb(lsc);
			lsc.setSqlPath("mycase/DeleteCaseImgByCaseId");
			exchangeDbService.deleteDb(lsc);
			return new JsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("delete error");
		}			
	}
		
	@RequestMapping("/saveCaseImg")
	@ResponseBody
	public JsonResult saveCaseImg(String caseId,String base64) {
		  hanldDiff();
		  System.out.println("imageBase64:"+base64);
		  base64 = base64.replaceAll("data:image/png;base64,", "");
		  System.out.println("imageBase640.0:"+base64);
		  try {
			  Date date = new Date();
			  SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			  int s =(int)(Math.random()*99+1);
			  String path = "/www/server/apache-tomcat-default/webapps/smart_cube/file/img/"+sdf.format(date)+s+".png";
			  System.out.println("path:"+path);
			  boolean b = commonServiceImpl.generateImage(base64, path);  //解密并保存图片
			  if(b) {
				  //数据库
				  String dbpath = "file/img/"+sdf.format(date)+s+".png";
				  Map<String,Object> data = new HashMap<String,Object>();
				  data.put("imgPath", dbpath);
				  data.put("caseId",caseId);
				  LscExchangeDb lsc = new LscExchangeDb();
				  lsc.setData(data);
				  lsc.setSqlPath("mycase/saveCaseImgPath");
				  exchangeDbService.saveDb(lsc);
			  }
			  return new JsonResult();
		  }catch(Exception e) {
			  e.printStackTrace();
			  return new JsonResult("error");
		  }
	}
	
	/**
	 * 保存病例信息 返回病例iD
	 * @param visitDate
	 * @param hospital
	 * @param season
	 * @return
	 */
	@RequestMapping("/saveCaseMsg")
	@ResponseBody
	public JsonResult saveCaseMsg(String phone,String feel,String caseId,String visitDate,String hospital,String season,HttpSession session) {
		hanldDiff();
		Object userId = session.getAttribute("userId");
		Map<String,Object> data = new HashMap<String,Object>();
		/**通过电话判断是否本人*/
		if(phone != null && phone.length()!= 0) {
			data.put("phone", phone);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("remiand/QryUserIdByPhone");
			List<Map<String,Object>> userList = exchangeDbService.selectDb(lsc);
			if(!userList.isEmpty()) {
				userId = userList.get(0).get("userId");
			}
		}
		data.put("visitDate", visitDate);
		data.put("hospital", hospital);
		data.put("season",season);
		data.put("userId", userId);
		data.put("feel", feel);
		if(caseId!=null&&caseId.length()>0) {
			data.put("caseId", caseId);
		}else {
			caseId = commonServiceImpl.getOnlyKey()+"";
			data.put("caseId", caseId);
		}
		List<String> list = new ArrayList<String>();
		list.add(caseId);
		LscExchangeDb db = new LscExchangeDb();
		db.setData(data);
		db.setSqlPath("mycase/saveCaseMsg");
		try {
			exchangeDbService.saveDb(db);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
	
	/**
	 * 通过病例Id查询所属图片路径
	 * @param caseId
	 * @return
	 */
	@RequestMapping("/showCaseImg")
	@ResponseBody
	public JsonResult showCaseImg(String caseId) {
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("caseId", caseId);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		LscExchangeDb db = new LscExchangeDb();
		db.setData(data);
		db.setSqlPath("mycase/QryCaseImgPathByCaseId");
		try {
			list = exchangeDbService.selectDb(db);
			System.out.println("imgpath:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}	
	}
	
	/**
	 * 查询个人病例
	 * @param session
	 * @return
	 */
	@RequestMapping("/showAllCase")
	@ResponseBody
	public JsonResult showAllCase(HttpSession session,String phone) {
		System.out.println("showAllCase");
		hanldDiff();	
		Map<String,Object> data = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Object userId = session.getAttribute("userId");
		/**通过电话判断是否本人*/
		if(phone != null && phone.length()!= 0) {
			data.put("phone", phone);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("remiand/QryUserIdByPhone");
			List<Map<String,Object>> userList = exchangeDbService.selectDb(lsc);
			if(!userList.isEmpty()) {
				userId = userList.get(0).get("userId");
			}
		}	
		if(userId==null) {
			data.put("userId",123);
		}else {
			data.put("userId",userId);
		}	
		LscExchangeDb db = new LscExchangeDb();
		db.setData(data);
		db.setSqlPath("mycase/QryCaseByUserId");
		try {
			list = exchangeDbService.selectDb(db);
			System.out.println("allcase:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
		
	}
	/**
	 * 展示最新一条case
	 * @param session
	 * @return
	 */
	@RequestMapping("/showNewCase")
	@ResponseBody
	public JsonResult showNewCase(HttpSession session,String phone,String caseId) {
		System.out.println("showNewCase");
		hanldDiff();
		Object userId = session.getAttribute("userId");
		Map<String,Object> data = new HashMap<String,Object>();
		/**通过电话判断是否本人*/
		System.out.println("ph:"+phone);
		if(phone!= null && phone.length()!= 0) {
			System.out.println("jin");
			data.put("phone", phone);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("remiand/QryUserIdByPhone");
			List<Map<String,Object>> userList = exchangeDbService.selectDb(lsc);
			if(!userList.isEmpty()) {
				userId = userList.get(0).get("userId");
			}
		}	
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(userId==null) {
			data.put("userId",123);
		}else {
			data.put("userId",userId);
		}	
		System.out.println("caseId:"+caseId);
		LscExchangeDb db = new LscExchangeDb();
		if(caseId!=""&& caseId.length()!= 0) {
			data.put("caseId", caseId);
			db.setData(data);
			db.setSqlPath("mycase/QryCaseByCaseId");
		}else {
			db.setData(data);
			db.setSqlPath("mycase/QryNewCaseByUserId");
		}
		try {
			
			list = exchangeDbService.selectDb(db);
			System.out.println("allcase:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
		
	}

}
