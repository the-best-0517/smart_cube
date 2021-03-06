package cn.buu.smart_cube.remiand.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
	
	@RequestMapping("/QryPillIdByPillDesc")
	@ResponseBody
	public JsonResult QryPillIdByPillDesc(String pillDesc) {
		System.out.println("QryPillIdByPillDesc");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("pillDesc", pillDesc);
		LscExchangeDb lsc =new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryPillIdByPillDesc");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		return new JsonResult(list);		
	}
	
	
	/**
	 * 判断药品是否存在
	 * @param pillDesc
	 * @return
	 */
	@RequestMapping("/ifExit")
	@ResponseBody
	public boolean ifExit(String pillDesc) {
		System.out.println("ifExit");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("pillDesc", pillDesc);
		LscExchangeDb lsc =new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryPillifExit");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		if(list.size()==0) {
			return false;	
		}else {
			return true;
		}
		
	}
	
	/**
	 * 药品名称模糊提醒
	 * @param pillDesc
	 * @return
	 */
	@RequestMapping("/selectBlur")
	@ResponseBody
	public JsonResult selectBlur(String pillDesc) {
		System.out.println("selectBlur");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("pillDesc", pillDesc);
		LscExchangeDb lsc =new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryBlur");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		return new JsonResult(list);		
	}
	
	
	/**
	 * 检查是否有新通知
	 * @param session
	 * @return
	 */
	@RequestMapping("/checkNotic")
	@ResponseBody
	public boolean checkNotic(HttpSession session) {
		System.out.println("checkNotic");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryIfNotic");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		if(list.isEmpty()) {
			return false;	
		}else {
			return true;
		}
		
	}
	
	/**
	 * 加载全部消息
	 * @param session
	 * @return
	 */
	@RequestMapping("/notic")
	@ResponseBody
	public JsonResult notic(HttpSession session) {
		System.out.println("notic");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/updateIfRead");
		exchangeDbService.saveDb(lsc);
		
		lsc.setSqlPath("remiand/QryAppNoticByAll");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		System.out.println("listnotic:"+list);
		return new JsonResult(list);	
	}
	
	/***
	 * 网页端 药品记录
	 * @param userId
	 * @return
	 */
	@RequestMapping("/showEatRemaind")       
	 @ResponseBody
	 public JsonResult showEatRemaind(String userId) {
	  System.out.println("showEatRemaind");
	  hanldDiff();
	  Map<String,Object> data = new HashMap<String, Object>();
	  List<Map<String,Object>> list = null;
	  if(userId!=null) {
		  data.put("userId", userId);
	  }else {
		  data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
	  }
	  System.out.println("userId:"+session.getAttribute("userId"));
	   LscExchangeDb lsc = new LscExchangeDb();
	   lsc.setData(data);
	   lsc.setSqlPath("remiand/QryEatRemaind");  
	  try {
	   list = exchangeDbService.selectDb(lsc);
	   System.out.println("EatRemindList:"+list);
	  }catch(Exception e) {
	   e.printStackTrace();
	  }
	  return new JsonResult(list);
	  
	 }
	
	
	
	/**
	 * 更新提醒时间
	 * @param futrueTime
	 * @param boxId
	 * @param session
	 * @return
	 */
	@RequestMapping("/upRemindTime")
	@ResponseBody
	public JsonResult upRemindTime(String futrueTime,String boxId,HttpSession session) {
		System.out.println("upRemindTime");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>(16);
		data.put("remindTime",futrueTime );
		data.put("boxId", boxId);
		data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/updateRemindTime");
		try {
			exchangeDbService.saveDb(lsc);
			return new JsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}	
	}
	
	
	/**
	 * 保存药品序列号（增加拓展盒时用）
	 * @param boxSerial
	 * @param session
	 * @return
	 */
	@RequestMapping("/insertboxSerial")
	@ResponseBody
	public JsonResult insertboxSerial(String boxSerial,HttpSession session) {
		System.out.println("insertboxSerial");
		hanldDiff();
		Map<String,Object> data = new HashMap<String,Object>(16);
		List<Map<String,Object>> list = null;
		data.put("boxSerial",boxSerial);

		data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryBoxNumByUserId");
		try {
			list = exchangeDbService.selectDb(lsc);
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}
		
		/***
		 * 设置药盒编号（用于药盒的指令）
		 */
//		List<Integer> ll = new ArrayList<Integer>();
//		for(int i=0;i<list.size();i++) {
//			ll.add(Integer.parseInt(list.get(i).get("boxNum").toString()));
//		}
//		for(int i=1;i<10;i++) {
//			if(!ll.contains(i)) {
//				data.put("boxNum",i);
//				break;
//			}
//		}
		int num = list.size()+1;
		data.put("boxNum",num);
		boolean b = check(boxSerial,data);
		lsc.setData(data);
		if(b) {
			lsc.setSqlPath("remiand/insertBoxSerial");
			exchangeDbService.saveDb(lsc);
		}else {
			//lsc.setSqlPath("remiand/updateBoxSerial");
		}		 
		 lsc.setSqlPath("remiand/QryBoxSerial");
		 list = exchangeDbService.selectDb(lsc);
		 boxSerial = list.get(0).get("boxSerial").toString();
		 String boxNum = list.get(0).get("boxNum").toString();
		 String set = "S0"+boxNum+boxSerial;
		 List<String> l = new ArrayList<String>();
		 l.add(set);
		return new JsonResult(l);	
	}
	
	private boolean check(String boxSerial, Map<String, Object> data) {
		/**判断序列号是否重复*/
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryByBoxSerial");
		list = exchangeDbService.selectDb(lsc);
		if(list.size()==0) {
			System.out.println("序列号不重复");
			return true;
		}
		return false;
	}

	/**
	 * 更新吃药状态
	 * @author 15874
	 * @param boxId
	 * @param session
	 * @return
	 */
	@RequestMapping("/changeIfEating")
	@ResponseBody
	public JsonResult changeIfEating(String boxId,HttpSession session) {
		System.out.println("changeIfEating");
		hanldDiff();
	    Map<String,Object> data = new HashMap<String,Object>(16);
	    data.put("boxId",boxId);
	    data.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
	    LscExchangeDb lsc = new LscExchangeDb();
	    lsc.setData(data);
	    lsc.setSqlPath("remiand/updateIfEating");
	    try {
	    	exchangeDbService.saveDb(lsc);
	    	return new JsonResult();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	return new JsonResult("error");
	    }	
	}
	
	/**
	 * 删除提醒事项
	 * @author 15874
	 * @param remindId
	 * @return
	 */
	@RequestMapping("/deleteRemindMsg")
	@ResponseBody
	public JsonResult deleteRemindMsg(String remindId) {
		System.out.println("deleteRemindMsg");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("remindId", remindId);
		LscExchangeDb lsc = new LscExchangeDb();
		/**通过remindID 查当前条的盒子号和提醒时间*/
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryRemindMsgByRemindId");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		data.put("boxId", list.get(0).get("boxId"));
		data.put("remindTime",list.get(0).get("remindTime"));
		lsc.setData(data);
		lsc.setSqlPath("remiand/DeleteRemindMsg");
		try {
			exchangeDbService.deleteDb(lsc);
			return new JsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");
		}
	}
	/**
	 * 保存编辑信息
	 * @throws ParseException 
	 */
	@RequestMapping("/saveRemindEdit")
	@ResponseBody
	public JsonResult saveRemindEdit(String remindId,String remindEdit/*,String remindBox*/) {
		System.out.println("saveRemindEdit");
		hanldDiff();
		System.out.println("remindId:"+remindId);
		System.out.println("remindEdit:"+remindEdit);
		remindEdit = remindEdit.replace("T", " ");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		Object d = sdf.parse(remindEdit);
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("remindId", remindId);
		data.put("remindEdit", remindEdit);
		//remindBox = remindBox.replaceAll("号盒", "");
		//data.put("remindBox", remindBox);
		LscExchangeDb lsc = new LscExchangeDb();
		/**通过remindID 查当前条的盒子号和提醒时间*/
		lsc.setData(data);
		lsc.setSqlPath("remiand/QryRemindMsgByRemindId");
		List<Map<String,Object>> list = exchangeDbService.selectDb(lsc);
		data.put("boxId", list.get(0).get("boxId"));
		data.put("remindTime",list.get(0).get("remindTime"));
		lsc.setData(data);
		lsc.setSqlPath("remiand/updateRemindMsg");
		try {
			exchangeDbService.saveDb(lsc);
			return new JsonResult();	
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult("error");	
		}
	}
	
	/**
	 * ��ѯ���п�������
	 * @param session
	 * @return
	 */
	@RequestMapping("/showRemaind")
	@ResponseBody
	public JsonResult showRemaind(HttpSession session,String phone) {
		System.out.println("showRemaind");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		List<Map<String,Object>> list = null;
		data.put("userId",session.getAttribute("userId"));
		if(!"".equals(phone)) {
			data.put("phone", phone);
			//通过电话号码查询userId
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("remiand/QryUserIdByPhone");
			list = exchangeDbService.selectDb(lsc);
			data.put("userId",list.get(0).get("userId"));
		}		
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
	public JsonResult addPills(String remindTime,String boxId,HttpSession session) {
		System.out.println("addPills");
		hanldDiff();
		List<Map<String,Object>> list = null;
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("remindTime",remindTime);
		data.put("boxId",boxId);
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
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
	 * @throws ParseException 
	 */
	@RequestMapping("/makeRemiandBypills")
	@ResponseBody
	public JsonResult makeRemiandBypills(@RequestBody String jsonPills,HttpSession session) throws UnsupportedEncodingException, ParseException {
		hanldDiff();
		jsonPills = java.net.URLDecoder.decode(jsonPills,"UTF-8");
		System.out.println("makeRemiandBypills:"+jsonPills);
		
		int x = jsonPills.lastIndexOf("&", jsonPills.lastIndexOf("&",jsonPills.lastIndexOf("&")-1)-1) ;
		String str = jsonPills;
		String pt = str.substring(x);
		String[] pts = pt.split("&");
		//家属电话
		String[] p1 = pts[1].split("=");
		String phone = p1.length>1?p1[1]:null;
		System.out.println("phone:"+phone);
		//天数
		String[] t1 = pts[2].split("=");
		String times = t1.length>1?t1[1]:"2";
		System.out.println("times:"+times);
		//病例号
		String[] c1 = pts[3].split("=");
		String caseId = c1.length>1?c1[1]:"1";
		System.out.println("caseId:"+caseId);
		
		jsonPills = jsonPills.substring(0, x);
		System.out.println("jsonPills:::"+jsonPills);
		
		List<String> list = new ArrayList<String>();
		List<Map<String,String>> l = new ArrayList<Map<String,String>>();
		String[] jsons = jsonPills.split("&");
		for(int i=0;i<jsons.length;i++) {
			String[] s = jsons[i].split("=");
			list.add(s[1]);
		}
		Map<String,String> data = null;
		for(int i=0;i<list.size();i++) {
			if(i==0||i%4==0) {
				data = new HashMap<String, String>();
				data.put("pillDesc",list.get(i));
			}
			if(i%4==1) {
				data.put("EatDay",list.get(i));
			}	
			if(i%4==2) {
				data.put("EatTimes",list.get(i));
			}
			if(i%4==3) {
				data.put("whereEating", list.get(i));
				l.add(data);
			}
		}
		System.out.println("l:"+l);
		/**创建病例服药记录*/
		makeCasePill(caseId,l);
		/**根据药品信息生成提醒事项*/
		System.out.println("times:"+times);
		for(int i=0;i<Integer.parseInt(times);i++) {
			makeRemiandBypills(caseId,l,session,phone,i);
		}
		
		return new JsonResult();		
	}	

	public void makeCasePill(String caseId, List<Map<String, String>> l) {
		Map<String,Object> data = new HashMap<String,Object>();
		LscExchangeDb lsc = new LscExchangeDb();
		data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
		data.put("caseId",caseId);
		if(caseId.equals("1")) {
			//新建病例
			 long c = commonService.getOnlyKey();
			 data.put("caseId", c);
			 lsc.setData(data);
			 lsc.setSqlPath("mycase/insertCase");
			 exchangeDbService.saveDb(lsc);
		}
		for(int i=0;i<l.size();i++) {
			data.put("pillDesc", l.get(i).get("pillDesc"));
			String EatTimes = l.get(i).get("EatTimes").toString().toString();
			int dose = Integer.parseInt(EatTimes.substring(EatTimes.indexOf("次")+1, EatTimes.length()-1));
			data.put("dose", dose);
			lsc.setData(data);
			lsc.setSqlPath("remiand/QryPillIdByPillDesc");
			List<Map<String,Object>> pillId = exchangeDbService.selectDb(lsc);
			System.out.println("pill:"+pillId);
			if(pillId.isEmpty()) {
				System.out.println("没有此药品");
				lsc.setData(data);
				lsc.setSqlPath("remiand/insertCasePillNoPillId");
				try {
					exchangeDbService.saveDb(lsc);
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					continue;
				}
				
			}
			data.put("pillId", pillId.get(0).get("pillId"));
			lsc.setData(data);
			lsc.setSqlPath("remiand/replaceCasePill");
			try {
				exchangeDbService.saveDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	/**
	 * 生成智能提醒记录
	 * @param list			药品集合
	 * @param session
	 * @param phone			家属模式专用。（家属电话）
	 * @param time			天数
	 * @throws ParseException
	 */
	List<Map<String,Object>> remiandList = new ArrayList<Map<String, Object>>();
	private void makeRemiandBypills(String caseId,List<Map<String, String>> list,HttpSession session,String phone,int time) throws ParseException {		
		for(int i=0;i<list.size();i++) {
			/**获取一天的次数  times   每顿的个数  dose*/
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
			String EatDay =  list.get(i).get("EatDay").toString();
			int end = EatDay.indexOf("次");
			int start = EatDay.indexOf("日");
			int times = Integer.parseInt(EatDay.substring(start+1, end));
			System.out.println("times:"+times);
			String EatTimes = list.get(i).get("EatTimes").toString();
			start = EatTimes.indexOf("次");
			int dose = Integer.parseInt(EatTimes.substring(start+1, EatTimes.length()-1));
			System.out.println("dose:"+dose);
			/**分类讨论设置时间*/
			Date date = new Date();
		 	Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DAY_OF_MONTH, +time);//+1今天的时间加一天
	        time++;
	        date = calendar.getTime();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        String tomrrow = sdf.format(date);
			/*未来：
			 *  拿到设定的吃饭时间
			 * */
	        /**获取个人习惯，吃饭时间*/
//	        lsc = new LscExchangeDb();
//	        map.put("userId", session.getAttribute("userId")==null?123:session.getAttribute("userId"));
//	        lsc.setData(map);
//	        lsc.setSqlPath("remiand/QryHabitByUserId");
//	        List<Map<String,Object>> habit = null;
//	        try {
//	        	habit = exchangeDbService.selectDb(lsc);
//	        }catch(Exception e) {
//	        	e.printStackTrace();
//	        }
//	        String breakfast = habit.get(0).get("breakfast").toString()==null?"08:00":habit.get(0).get("breakfast").toString();
//	        String lunch = habit.get(0).get("lunch").toString()==null?"12:00":habit.get(0).get("lunch").toString();
//	        String dinner = habit.get(0).get("dinner").toString()==null?"18:00":habit.get(0).get("dinner").toString();
	        
			List<String> remaindTime = new ArrayList<String>();
			switch(times) {
			case 1: if("空腹".equals(whereEating)||"饭前".equals(whereEating)) {
						remaindTime.add(tomrrow+" 07:30");
					}else if("饭后".equals(whereEating)) {
						remaindTime.add(tomrrow+" 08:30");
					}
			break;
			case 2:if("饭前".equals(whereEating)) {
						remaindTime.add(tomrrow+" 07:30");
						remaindTime.add(tomrrow+" 17:30");
					}else if("饭后".equals(whereEating)) {
						remaindTime.add(tomrrow+" 08:30");
						remaindTime.add(tomrrow+" 18:30");
					}
			break;
			case 3:if("饭前".equals(whereEating)) {
						remaindTime.add(tomrrow+" 07:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 17:30");
					}else if("饭后".equals(whereEating)) {
						remaindTime.add(tomrrow+" 08:30");
						remaindTime.add(tomrrow+" 12:30");
						remaindTime.add(tomrrow+" 18:30");
					}
			break;
			case 4:if("饭前".equals(whereEating)) {
						remaindTime.add(tomrrow+" 07:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 15:30");
						remaindTime.add(tomrrow+" 18:30");
				   }else if("饭后".equals(whereEating)){
					   	remaindTime.add(tomrrow+" 07:30");
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
				data.put("caseId", caseId);
				System.out.println("phone:"+phone);
				try {
					if(phone != null && phone.length()!= 0) {
						data.put("phone", phone);
						//通过电话号码查询userId
						//LscExchangeDb lsc = new LscExchangeDb();
						lsc.setData(data);
						lsc.setSqlPath("remiand/QryUserIdByPhone");
						List<Map<String,Object>> l = exchangeDbService.selectDb(lsc);
						data.put("userId",l.get(0).get("userId"));
					}	
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				Object userId = session.getAttribute("userId");
				if(data.get("userId")==null) {
					data.put("userId",userId==null?123:userId);
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
				List<Integer> l = new ArrayList<Integer>();
				String rt = remaindTime.get(k);
				System.out.println("remiandList:"+remiandList);
				for(int m=0;m<remiandList.size();m++) {
					l.add(Integer.parseInt(remiandList.get(m).get("boxId").toString()));
					if(rt.equals(remiandList.get(m).get("remindTime"))) {
						data.put("boxId",remiandList.get(m).get("boxId"));
						break;
					}
				}
				if(data.get("boxId")==null) {
					//数据库中寻找相同时间的盒子号
					lsc = new LscExchangeDb();
					data.put("rt", rt);
					lsc.setData(data);
					lsc.setSqlPath("remiand/QrySameTimeBoxId");
					try {
						List<Map<String,Object>> dbBoxId = exchangeDbService.selectDb(lsc);
						if(dbBoxId.size()>0) {
							data.put("boxId", dbBoxId.get(0).get("boxId"));
						}					
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
				if(data.get("boxId")==null) {
					  //查询提醒表里的盒子	(判断那个盒子是空的)
					List<Map<String,Object>> boxIdList = new ArrayList<Map<String,Object>>();
					List<Map<String,Object>> boxSerialList = new ArrayList<Map<String,Object>>();
					List<Map<String,Object>> squareNumberList = new ArrayList<Map<String,Object>>();				
					lsc.setData(data);
					 lsc.setSqlPath("remiand/QryEmptyBoxId"); 
					 try {
						 boxIdList = exchangeDbService.selectDb(lsc);
						 for(int n=0;n<boxIdList.size();n++) {
							 l.add(Integer.parseInt(boxIdList.get(n).get("box_id").toString()));
						 }
					 }catch(Exception e) {
						 e.printStackTrace();
					 }
					 //查询一共多少盒子
					 lsc.setSqlPath("remiand/QryAllBoxSerial");
					 int num = 0;
					 try {
						 boxSerialList = exchangeDbService.selectDb(lsc);
						 for(int p=0;p<boxSerialList.size();p++) {
							 data.put("boxSerial", boxSerialList.get(p).get("box_serial"));
							 lsc.setSqlPath("remiand/QryAllBoxNum");
							 squareNumberList = exchangeDbService.selectDb(lsc);
							 
							 for(int n=0;n<squareNumberList.size();n++) {
								num = num+ Integer.parseInt((squareNumberList.get(n).get("squareNumber").toString()==null)?"0":squareNumberList.get(n).get("squareNumber").toString());
							 }
						 }
					 }catch(Exception e) {
						 e.printStackTrace();
					 }
					 System.out.println("l:"+l);				 
					 for(int n=1;n<=num;n++) {
						 System.out.println(l.contains(n));
						 if(!l.contains(n)) {
							 data.put("boxId", n);    
							 break;
						 }
					 }
					      
				}
				if(data.get("boxId")==null) {
					//无盒可用
					System.out.println("盒不够了");
					data.put("boxId", 0);
					//return;
				}//else {
				//判断时间 小于当前时间的过滤掉
				date = new Date();
				long nowDate = date.getTime();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");		
				long ret = 0;
				System.out.println("remaindTime:"+remaindTime);
				System.out.println(remaindTime.get(k));
				System.out.println(sdf.parse(remaindTime.get(k)));
				try {
					ret = sdf.parse(remaindTime.get(k)).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(ret>nowDate) {
					remiandList.add(data);
				}
			//	}
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
				if(i==remiandList.size()-1) {
					//保存成功清空reminadList
					remiandList.clear();
				}
				/**添加定时提醒任务*/
				try {
				//	commonService.TimerRemindTask(remiandList.get(i).get("remindTime"),0);
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
