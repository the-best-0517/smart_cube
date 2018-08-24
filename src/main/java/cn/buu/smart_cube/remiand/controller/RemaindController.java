package cn.buu.smart_cube.remiand.controller;

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
@RequestMapping("/remiand")
public class RemaindController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;

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
}
