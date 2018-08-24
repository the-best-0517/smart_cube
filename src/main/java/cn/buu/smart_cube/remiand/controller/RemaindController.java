package cn.buu.smart_cube.remiand.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import cn.buu.smart_cube.common.service.CommonService;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/remiand")
public class RemaindController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	@Resource
	private CommonService commonService;
	@Resource
	private HttpSession session;
	/**
	 * ��ѯ���п�������
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
	 * ���ݺб�Ų�ѯ����ҩƷ
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
	 * ͨ��ҩƷ��Ʒ�ţ�pill_id����ѯҩƷ��Ϣ
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
	/**
	 * 解析药品数据（用于提醒）
	 * @param jsonPills
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/makeRemiandBypills")
	@ResponseBody
	public JsonResult makeRemiandBypills(@RequestBody String jsonPills) throws UnsupportedEncodingException {
		hanldDiff();
		jsonPills = java.net.URLDecoder.decode(jsonPills,"UTF-8");

		System.out.println("makeRemiandBypills:"+jsonPills);
//		
//		jsonPills = jsonPills.replaceAll("jsonPills%5B", "");
//		jsonPills = jsonPills.replaceAll("%5D", "");
//		System.out.println("makeRemiandBypills:"+jsonPills);
		List<String> list = new ArrayList<String>();
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		String[] jsons = jsonPills.split("&");
		for(int i=0;i<jsons.length;i++) {
			String[] s = jsons[i].split("=");
			list.add(s[1]);
		}
		Map<String,String> data = null;
		for(int i=0;i<list.size();i++) {
			if(i==0||i%3==0) {
				data = new HashMap<String, String>();
				data.put("pillDesc",list.get(i));
			}
			if(i%3==1) {
				data.put("instructions",list.get(i));
			}	
			if(i%3==2) {
				data.put("whereEating", list.get(i));
				l.add(data);
			}
		}
		System.out.println("l:"+l);
		/**根据药品信息生成提醒事项*/
		makeRemiandBypills(l);
		return new JsonResult();		
	}
	/**
	 * �������Ѽ�¼
	 * @param list
	 */
	List<Map<String,Object>> remiandList = new ArrayList<Map<String, Object>>();
	private void makeRemiandBypills(List<Map<String, String>> list) {		
		for(int i=0;i<list.size();i++) {
			/**获取一天的次数  times   每顿的个数  dose*/
			String instructions = list.get(i).get("instructions");
			String whereEating = list.get(i).get("whereEating");  //饭前或者饭后或者空肚
			if(whereEating.equals(null)) {
				whereEating = "饭后";
			}
			String pillDesc = list.get(i).get("pillDesc");
			/**通过药品名查找药品id*/
			List<Map<String,Object>> pillIdList = null;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pillDesc", pillDesc);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(map);
			lsc.setSqlPath("remaind/QryPillIdByPillDesc");
			try {
				pillIdList = exchangeDbService.selectDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			String[] s = instructions.split(",");
			int end = s[0].indexOf("次");
			int start = s[0].indexOf("天");
			int times = Integer.parseInt(s[0].substring(start+1, end));
			System.out.println("times:"+times);
			start = s[1].indexOf("次");
			int dose = Integer.parseInt(s[1].substring(start+1, s[1].length()-1));
			System.out.println("dose:"+dose);
			/**分类讨论设置时间*/
			/*未来：
			 *  拿到设定的吃饭时间
			 * */
			List<String> remaindTime = new ArrayList<String>();
			switch(times) {
			case 1: if(whereEating.equals("空腹")||whereEating.equals("饭前")) {
						remaindTime.add("7:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add("8:30");
					}
			break;
			case 2:if(whereEating.equals("饭前")) {
						remaindTime.add("7:30");
						remaindTime.add("17:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add("8:30");
						remaindTime.add("18:30");
					}
			break;
			case 3:if(whereEating.equals("饭前")) {
						remaindTime.add("7:30");
						remaindTime.add("11:30");
						remaindTime.add("17:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add("8:30");
						remaindTime.add("12:30");
						remaindTime.add("18:30");
					}
			break;
			case 4:if(whereEating.equals("饭前")) {
						remaindTime.add("7:30");
						remaindTime.add("11:30");
						remaindTime.add("15:30");
						remaindTime.add("18:30");
				   }else if(whereEating.equals("饭后")){
					   	remaindTime.add("7:30");
						remaindTime.add("11:30");
						remaindTime.add("15:30");
						remaindTime.add("18:30");
				   }
			break;
			default:  otherPlan();
			}
			//生成list记录
			/*未来可以有选择添加几天的*/
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("userId",session.getAttribute("userId"));
			data.put("pillId",pillIdList.get(0).get("pillId"));		
			data.put("dose", dose);
			for(int k=0;k<remaindTime.size();k++) {
				long remainId = commonService.getOnlyKey();
				data.put("remaindId", remainId);
				/**通过时间 在remaindList中找到相同时间的盒id*/
				String rt = remaindTime.get(k);
				for(int m=0;m<remiandList.size();i++) {
					if(rt.equals(remiandList.get(m).get("remindTime"))) {
						data.put("boxId",remiandList.get(m).get("boxId"));
						break;
					}
				}
				if("".equals(data.get("boxId"))) {
					data.put("boxId", 1);              //未来会有判断	(判断那个盒子是空的)
				}			
				remiandList.add(data);
			}
			
		}
		/**保存记录到记录表*/
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("remiand/saveRemind");
		for(int i=0;i<remiandList.size();i++) {			
			lsc.setData(remiandList.get(i));
			try {
				exchangeDbService.saveDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * //其他方案
	 */
	private void otherPlan() {
			
	}
}
