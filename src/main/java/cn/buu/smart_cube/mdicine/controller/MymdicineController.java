package cn.buu.smart_cube.mdicine.controller;

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
@RequestMapping("/medicine")
public class MymdicineController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonService commonService;
	
	/**
	 * 
	 * @param x     		 x 坐标
	 * @param y
	 * @param whichMedBox    拼插药盒  哪个药盒  主盒还是副盒(用于判断移动到哪个小药盒里边)
	 * @param pillDesc		  拖拽的药品描述
	 * @return
	 */
	@RequestMapping("/checkMove")
	@ResponseBody
	public JsonResult checkMove(HttpSession session,String x,String y,String whichMedBox,String pillDesc,String boxId) {
		System.out.println("checkMove");
		hanldDiff();
		Map<String,Object>  data = new HashMap<String,Object>();
		data.put("pillDesc", pillDesc.replaceAll("\\d", ""));
		data.put("boxId", boxId);
		Object userId = session.getAttribute("userId");
		data.put("userId", userId==null?123:userId);
		int goalBox = 0;
		if(37<=Integer.parseInt(x)&&Integer.parseInt(x)<=106) {
			if(75<=Integer.parseInt(y)&&Integer.parseInt(y)<=146) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+1;
			}
			if(200<=Integer.parseInt(y)&&Integer.parseInt(y)<=370) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+4;
			}
		}
		if(267<=Integer.parseInt(x)&&Integer.parseInt(x)<=336) {
			if(75<=Integer.parseInt(y)&&Integer.parseInt(y)<=146) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+2;
			}
			if(200<=Integer.parseInt(y)&&Integer.parseInt(y)<=370) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+5;
			}
		}
		if(497<=Integer.parseInt(x)&&Integer.parseInt(x)<=566) {
			if(75<=Integer.parseInt(y)&&Integer.parseInt(y)<=146) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+3;
			}
			if(200<=Integer.parseInt(y)&&Integer.parseInt(y)<=370) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+6;
			}
		}
		if(goalBox==0) {
			return new JsonResult("error");
		}
		data.put("goalBox", goalBox);
		List<Integer> list = new ArrayList<Integer>();
		list.add(goalBox);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("medicine/updateRemindMeg");
		try {
			exchangeDbService.saveDb(lsc);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}
		
	}
	
	/**
	 * 通过boxId查询当中记录
	 * @param boxId
	 * @param session
	 * @return
	 */
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













