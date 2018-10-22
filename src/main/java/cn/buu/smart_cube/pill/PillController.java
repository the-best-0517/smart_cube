package cn.buu.smart_cube.pill;

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
import cn.buu.smart_cube.common.web.JsonResult;
@Controller
@RequestMapping("/pill")
public class PillController extends CommonController{
	 @Resource
	 private ExchangeDbService exchangeDbService;
	 
	 
	 @RequestMapping("/deletePill")
	 @ResponseBody
	 public JsonResult deletePill(String pillId) {
		 System.out.println("deletePill");
		 hanldDiff();
		 Map<String,Object> data = new HashMap<String, Object>(16);	
		 data.put("pillId", pillId);
		 LscExchangeDb lsc = new LscExchangeDb();
		 lsc.setData(data);
		 lsc.setSqlPath("pill/deletePillBypillId");
		 try {
			 exchangeDbService.deleteDb(lsc);
			 return new JsonResult();	
		 }catch(Exception e) {
			 e.printStackTrace();
			 return new JsonResult("error");	
		 }
		 
	 }
	 
	 
	 /**
	  * 保存药品评价 及其星级评价
	  * @param starText	  几星
	  * @param feelText	 评价
	  * @param pillId	 药品id
	  * @param session
	  * @return
	  */
	 @RequestMapping("/saveAssess")
	 @ResponseBody
	 public JsonResult saveAssess(String starText,String feelText,String pillId,HttpSession session) {
		 System.out.println("saveAssess");
		 hanldDiff();
		 Map<String,Object> data = new HashMap<String, Object>(16);	
		 data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		 data.put("starText",starText);
		 data.put("feelText",feelText);
		 data.put("pillId",pillId);
		 LscExchangeDb lsc = new LscExchangeDb();
		 lsc.setData(data);
		
		 try {
			 lsc.setSqlPath("pill/insertPost");
			 exchangeDbService.saveDb(lsc);
			 lsc.setSqlPath("pill/insertStar");
			 exchangeDbService.saveDb(lsc);
			 return new JsonResult();
		 }catch(Exception e) {
			 e.printStackTrace();
			 return new JsonResult("error");
		 }	
	 }
	 
	 
	 /**
	  * 展示药品信息根据key
	  * @param key  关键字
	  * @return
	  */
	 @RequestMapping("/showAllPill")
	 @ResponseBody
	 public JsonResult showAllPill(String key) {
		 System.out.println("showAllpill");
		 System.out.println("key:"+key);
		 hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();	
		if(key==null) {
			data.put("key","");
		}else {
			 data.put("key", key);	
		}	   	
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("pill/QryPillByKey");
		try {
			list = exchangeDbService.selectDb(lsc);
			System.out.println("list:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult(e);
		}		 
	 }
	 /**
	  * 加载药品评论
	  * @param pillId
	  * @param session
	  * @return
	  */
	 @RequestMapping("/showPost")
	 @ResponseBody
	 public JsonResult showPost(String pillId,HttpSession session) {
		System.out.println("showPost");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		Object userId = session.getAttribute("userId");
		if(userId==null) {
			data.put("userId",123);
		}else {
			data.put("userId",userId);
		}	
		data.put("pillId", pillId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("pill/QryPillPost");
		try {
			list = exchangeDbService.selectDb(lsc);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult(e);
		}
		
	 }
}
