package cn.buu.smart_cube.mdicine.controller;

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
@RequestMapping("/medicine")
public class MymdicineController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonService commonService;
	
	
	@RequestMapping("/selPillRecord")
	@ResponseBody
	public JsonResult selPillRecord(String boxId,HttpSession session) {
		System.out.println("selPillRecord");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		data.put("boxId", boxId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("medicine/QryPillRecord");
		lsc.setData(data);
		try {
			List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
			return new JsonResult(list);	
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
	
	@RequestMapping("/showMdicineBox")
	@ResponseBody
	public JsonResult showMdicineBox(HttpSession session) {
		System.out.println("showMdicineBox");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("medicine/QryMedicineMsg");
		lsc.setData(data);
		try {
			List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
			System.out.println("list:"+list);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}			
	}

}













