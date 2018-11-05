package cn.buu.smart_cube.article.controller;

import java.util.ArrayList;
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




/**
 * @ClassName: articleController
 * @Description: 文章
 * @author: wangwei
 * @date: 2018年11月5日 上午10:50:36
 */
@Controller
@RequestMapping("/article")
public class articleController extends CommonController{
 @Resource
 private ExchangeDbService exchangeDbService;
 @Resource
 private CommonServiceImpl commonServiceImpl;
 
 @RequestMapping("/showNewsTitleFirst")
	@ResponseBody
	public JsonResult showNewsTitleFirst() {
	 System.out.println("showNewsTitleFirst");
	 hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("type","news");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("article/QryTitleFirst");
		try {
			list = exchangeDbService.selectDb(db);
			System.out.println("firstNewsTitle:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
 
 
 
 @RequestMapping("/showKnowledgeTitleFirst")
 @ResponseBody
	public JsonResult showKnowledgeTitleFirst() {
	 System.out.println("showKnowledgeTitleFirst");
	 hanldDiff();
	 Map<String,Object> data = new HashMap<String,Object>();
		data.put("type","knowledge");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		LscExchangeDb db = new LscExchangeDb();
		db.setSqlPath("article/QryTitleFirst");
		try {
			list = exchangeDbService.selectDb(db);
			System.out.println("firstNewsTitle:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	 
 }
 
	
 @RequestMapping("/showNewsTitle")
 @ResponseBody
 public JsonResult showNewsTitle() {
	 System.out.println("showNewsTitle");
	 hanldDiff();
  Map<String,Object> data = new HashMap<String,Object>();
  data.put("type","news");
  List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
  LscExchangeDb db = new LscExchangeDb();
  db.setData(data);
  db.setSqlPath("article/QryTitle");
  try {
   list = exchangeDbService.selectDb(db);
   System.out.println("newsTitle:"+list);
   return new JsonResult(list);
  }catch(Exception e) {
   e.printStackTrace();
   return new JsonResult("error");
  }  
 }
 
 

	@RequestMapping("/showKnowledgeTitle")
	@ResponseBody
	public JsonResult showKnowledgeTitle() {
		System.out.println("showKnowledgeTitle");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		LscExchangeDb db = new LscExchangeDb();
		data.put("type","knowledge");
		db.setData(data);
		db.setSqlPath("article/QryTitle");
		try {
			list = exchangeDbService.selectDb(db);
			System.out.println("KnowledgeTitle:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
	
 	
}