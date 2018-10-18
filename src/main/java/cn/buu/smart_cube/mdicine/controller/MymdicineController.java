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
	 * 更新药品记录剂量/提醒表记录剂量
	 * @param boxId
	 * @param pillId
	 * @param dose
	 * @param session
	 * @return
	 */
	@RequestMapping("/saveEditPill")
	@ResponseBody
	public JsonResult saveEditPill(String boxId,String pillId,String dose,HttpSession session) {
		Map<String,Object> data = new HashMap<String,Object>(16);
		System.out.println("saveEditPill");
		hanldDiff();
		data.put("pillId", pillId);
		data.put("dose",dose);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		if(boxId==null||"".equals(boxId)) {
			//更新药品记录表
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("medicine/updateRecordPill");
			try {
				exchangeDbService.saveDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
			return new JsonResult();
		}else {
			//更新提醒表
			data.put("boxId", boxId);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("medicine/updateRePill");
			try {
				exchangeDbService.saveDb(lsc);
				return new JsonResult();
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
		}
		
		
	}
	
	/**
	 * 删除药品记录/提醒中的记录
	 * @param boxId
	 * @param pillId
	 * @param session
	 * @return
	 */
	@RequestMapping("/deleteBill")
	@ResponseBody
	public JsonResult deleteBill(String boxId,String pillId,HttpSession session) {
		Map<String,Object> data = new HashMap<String,Object>(16);
		System.out.println("deleteBill");
		hanldDiff();
		data.put("pillId", pillId);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		if(boxId==null||"".equals(boxId)) {
			//删除药品记录表
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("medicine/deletePill");
			try {
				exchangeDbService.deleteDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
		}else {
			//删除提醒表里
			data.put("boxId", boxId);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("medicine/deleteRePill");
			try {
				exchangeDbService.deleteDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");	
			}
		}
		return new JsonResult();		
	}
	
	/**
	 * 展示个人药品记录
	 * @param session
	 * @return
	 */
	@RequestMapping("/showBillRecord")
	@ResponseBody
	public JsonResult showBillRecord(HttpSession session){
		System.out.println("showBillRecord");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String,Object>(16);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("medicine/QryBillRecordByUserId");
		try {
			list = exchangeDbService.selectDb(lsc);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}		
	}
	
	/**
	 * @author 15874
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
		pillDesc = pillDesc.replaceAll("\n", "");
		pillDesc = pillDesc.replaceAll("删除", "");
		pillDesc = pillDesc.replaceAll("编辑", "");
		//String[] pillDescs = pillDesc.split("\n");
		//System.out.println("pillDescs:"+pillDescs);
		data.put("pillDesc", pillDesc.replaceAll("\\d", ""));
		data.put("boxId", boxId);
		Object userId = session.getAttribute("userId");
		data.put("userId", userId==null?123:userId);
		int goalBox = 0;
		if(40<=Integer.parseInt(x)&&Integer.parseInt(x)<=150) {
			if(65<=Integer.parseInt(y)&&Integer.parseInt(y)<=175) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+1;
			}
			if(175<Integer.parseInt(y)&&Integer.parseInt(y)<=285) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+4;
			}
//			if(285<Integer.parseInt(y)&&Integer.parseInt(y)<=395) {
//				goalBox = (Integer.parseInt(whichMedBox)-1)*6+7;
//			}
		}
		if(150<=Integer.parseInt(x)&&Integer.parseInt(x)<=260) {
			if(65<=Integer.parseInt(y)&&Integer.parseInt(y)<=175) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+2;
			}
			if(175<Integer.parseInt(y)&&Integer.parseInt(y)<=285) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+5;
			}
//			if(285<Integer.parseInt(y)&&Integer.parseInt(y)<=395) {
//				goalBox = (Integer.parseInt(whichMedBox)-1)*6+8;
//			}
		}
		if(260<Integer.parseInt(x)&&Integer.parseInt(x)<=370) {
			if(65<=Integer.parseInt(y)&&Integer.parseInt(y)<=175) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+3;
			}
			if(175<=Integer.parseInt(y)&&Integer.parseInt(y)<=285) {
				goalBox = (Integer.parseInt(whichMedBox)-1)*6+6;
			}
//			if(285<=Integer.parseInt(y)&&Integer.parseInt(y)<=395) {
//				goalBox = (Integer.parseInt(whichMedBox)-1)*6+9;
//			}
		}
		if(goalBox==0) {
			return new JsonResult("error");
		}
		data.put("goalBox", goalBox);
		/**检查目标盒中是否有当前药品*/
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("medicine/QryGoalBoxMedicine");
		List<Map<String,Object>> goalPillDescList = exchangeDbService.selectDb(lsc);
		for(Map<String,Object> m:goalPillDescList) {
			if(pillDesc.equals(m.get("pillDesc"))) {
				return new JsonResult("目标药盒中已存在！");
			}
		}
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(goalBox);
		List<Map<String,Object>> reList = null;
		if(boxId==null||"".equals(boxId)) {
			//LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("medicine/QryReMsgByGoalBox");
			try {
			reList = exchangeDbService.selectDb(lsc);
			data.put("pillId",reList.get(0).get("pillId")==null?00000:reList.get(0).get("pillId"));
			data.put("pillDesc", reList.get(0).get("pillDesc"));
			data.put("boxId", reList.get(0).get("boxId"));
			data.put("remindTime", reList.get(0).get("remindTime"));
			long remindId = commonService.getOnlyKey();
			data.put("remindId", remindId);
			data.put("dose",reList.get(0).get("dose"));		
			lsc.setData(data);
			lsc.setSqlPath("medicine/saveRemind");
			return new JsonResult(list);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
		}else {
			//LscExchangeDb lsc = new LscExchangeDb();
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













