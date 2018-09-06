package cn.buu.smart_cube.mycase.controller;

import java.util.ArrayList;
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
import cn.buu.smart_cube.common.service.CommonService;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/case")
public class CaseController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonService commonService;
	
	/**
	 * 保存病例信息 返回病例iD
	 * @param visitDate
	 * @param hospital
	 * @param season
	 * @return
	 */
	@RequestMapping("/saveCaseMsg")
	@ResponseBody
	public JsonResult saveCaseMsg(String visitDate,String hospital,String season,HttpSession session) {
		hanldDiff();
		Object userId = session.getAttribute("userId");
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("visitDate", visitDate);
		data.put("hospital", hospital);
		data.put("season",season);
		data.put("userId", userId);
		String caseId = commonService.getOnlyKey()+"";
		data.put("caseId", caseId);
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
	public JsonResult showAllCase(HttpSession session) {
		hanldDiff();
		Object userId = session.getAttribute("userId");
		Map<String,Object> data = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
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
}
