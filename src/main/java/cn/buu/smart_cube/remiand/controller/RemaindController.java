package cn.buu.smart_cube.remiand.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	public JsonResult addPills(String remindTime,String boxId) {
		System.out.println("addPills");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("remindTime",remindTime);
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
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult(e);
		}
				
	}
	/**
	 * 解析药品数据（用于提醒）
	 * @param jsonPills
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/makeRemiandBypills")
	@ResponseBody
	public JsonResult makeRemiandBypills(@RequestBody String jsonPills,HttpSession session) throws UnsupportedEncodingException {
		hanldDiff();
		jsonPills = java.net.URLDecoder.decode(jsonPills,"UTF-8");
		System.out.println("makeRemiandBypills:"+jsonPills);
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
		makeRemiandBypills(l,session);
		return new JsonResult();		
	}
	/**
	 * �������Ѽ�¼
	 * @param list
	 */
	List<Map<String,Object>> remiandList = new ArrayList<Map<String, Object>>();
	private void makeRemiandBypills(List<Map<String, String>> list,HttpSession session) {		
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
			lsc.setSqlPath("remiand/QryPillIdByPillDesc");
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
			Date date = new Date();
		 	Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
	        date = calendar.getTime();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String tomrrow = sdf.format(date);
			/*未来：
			 *  拿到设定的吃饭时间
			 * */				
			List<String> remaindTime = new ArrayList<String>();
			switch(times) {
			case 1: if(whereEating.equals("空腹")||whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 7:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add(tomrrow+" 8:30");
					}
			break;
			case 2:if(whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 7:30");
						remaindTime.add(tomrrow+" 17:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add(tomrrow+" 8:30");
						remaindTime.add(tomrrow+" 18:30");
					}
			break;
			case 3:if(whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 7:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 17:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add(tomrrow+" 8:30");
						remaindTime.add(tomrrow+" 12:30");
						remaindTime.add(tomrrow+" 18:30");
					}
			break;
			case 4:if(whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 7:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 15:30");
						remaindTime.add(tomrrow+" 18:30");
				   }else if(whereEating.equals("饭后")){
					   	remaindTime.add(tomrrow+" 7:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 15:30");
						remaindTime.add(tomrrow+" 18:30");
				   }
			break;
			default:  otherPlan();
			}
			//生成list记录
			/*未来可以有选择添加几天的*/			
			for(int k=0;k<remaindTime.size();k++) {		
				Map<String,Object> data = new HashMap<String, Object>();
				Object userId = session.getAttribute("userId");
				if(userId!=null) {
					data.put("userId",userId);
				}else {
					data.put("userId", 123);
				}
				if(pillIdList.size()>0) {
					data.put("pillId",pillIdList.get(0).get("pillId"));	
				}else {
					data.put("pillId", "00000");
				}				
				data.put("dose", dose);
				data.put("pillDesc",pillDesc);//绑定药品描述
				
				data.put("remindTime", remaindTime.get(k)); //提醒时间
				long remindId = commonService.getOnlyKey();
				data.put("remindId", remindId);
				/**通过时间 在remaindList中找到相同时间的盒id*/
				String rt = remaindTime.get(k);
				for(int m=0;m<remiandList.size();m++) {
					if(rt.equals(remiandList.get(m).get("remindTime"))) {
						data.put("boxId",remiandList.get(m).get("boxId"));
						break;
					}
				}
				if(data.get("boxId")==null) {
					data.put("boxId", 1);              //未来会有判断	(判断那个盒子是空的)
				}			
				remiandList.add(data);
			}
			
		}
		/**保存记录到记录表*/
		System.out.println("remiandLIst:"+remiandList);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setSqlPath("remiand/saveRemind");
		for(int i=0;i<remiandList.size();i++) {			
			lsc.setData(remiandList.get(i));
			try {
				exchangeDbService.saveDb(lsc);
				/**添加定时提醒任务*/
				try {
					commonService.TimerRemindTask(remiandList.get(i).get("remindTime"),0);
				}catch(Exception e) {
					e.printStackTrace();
				}
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
