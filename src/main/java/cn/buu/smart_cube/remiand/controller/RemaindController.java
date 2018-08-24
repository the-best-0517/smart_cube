package cn.buu.smart_cube.remiand.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/remiand")
public class RemaindController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	/**
	 * 查询所有可用提醒
	 * @param session
	 * @return
	 */
	@RequestMapping("/showRemaind")
	@ResponseBody
	public JsonResult showRemaind(HttpSession session) {
		System.out.println("showRemaind");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		List<Map<String,Object>> list = null;
		data.put("userId",session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("remiand/QryAllRemaind");
		lsc.setData(data);
		try {
			list = exchangeDbService.selectDb(lsc);
			System.out.println("remindList:"+list);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new JsonResult(list);
		
	}
	/**
	 * 根据盒编号查询盒中药品
	 * @param boxId
	 * @return
	 */
	@RequestMapping("/addPills")
	@ResponseBody
	public JsonResult addPills(String boxId) {
		System.out.println("addPills");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("boxId",boxId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("remiand/QryPillsByBoxId");
		lsc.setData(data);
		try {
			list = exchangeDbService.selectDb(lsc);
			System.out.println("pillList:"+list);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new JsonResult(list);
	}
	/**
	 * 通过药品产品号（pill_id）查询药品信息
	 * @param pillId
	 * @return
	 */
	@RequestMapping("/findPillMsgByPillId")
	@ResponseBody
	public JsonResult findPillMsgByPillId(String pillId) {
		System.out.println("findPillMsgByPillId:"+pillId);
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("pillId", pillId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("remiand/QryPillMsgByPillId");
		lsc.setData(data);
		try {
			list = exchangeDbService.selectDb(lsc);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new JsonResult(list);		
	}
	@RequestMapping("/makeRemiandBypills")
	@ResponseBody
	public JsonResult makeRemiandBypills(@RequestBody String jsonPills) {
		
		System.out.println("makeRemiandBypills:"+jsonPills);
		jsonPills = jsonPills.replaceAll("jsonPills%5B", "");
		jsonPills = jsonPills.replaceAll("%5D", "");
		System.out.println("makeRemiandBypills:"+jsonPills);
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		return new JsonResult();		
	}
}
