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
	 */
	@RequestMapping("/saveRemindEdit")
	@ResponseBody
	public JsonResult saveRemindEdit(String remindId,String remindEdit,String remindBox) {
		System.out.println("saveRemindEdit");
		hanldDiff();
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("remindId", remindId);
		data.put("remindEdit", remindEdit);
		remindBox = remindBox.replaceAll("号盒", "");
		data.put("remindBox", remindBox);
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
	 * @throws ParseException 
	 */
	@RequestMapping("/makeRemiandBypills")
	@ResponseBody
	public JsonResult makeRemiandBypills(@RequestBody String jsonPills,HttpSession session) throws UnsupportedEncodingException, ParseException {
		hanldDiff();
		jsonPills = java.net.URLDecoder.decode(jsonPills,"UTF-8");
		System.out.println("makeRemiandBypills:"+jsonPills);
		
		int x = jsonPills.lastIndexOf("&",jsonPills.lastIndexOf("&")-1);
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
		System.out.println("times:"+times);
		for(int i=0;i<Integer.parseInt(times);i++) {
			makeRemiandBypills(l,session,phone,i);
		}
		
		return new JsonResult();		
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
	private void makeRemiandBypills(List<Map<String, String>> list,HttpSession session,String phone,int time) throws ParseException {		
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
			case 1: if(whereEating.equals("空腹")||whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 07:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add(tomrrow+" 08:30");
					}
			break;
			case 2:if(whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 07:30");
						remaindTime.add(tomrrow+" 17:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add(tomrrow+" 08:30");
						remaindTime.add(tomrrow+" 18:30");
					}
			break;
			case 3:if(whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 07:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 17:30");
					}else if(whereEating.equals("饭后")) {
						remaindTime.add(tomrrow+" 08:30");
						remaindTime.add(tomrrow+" 12:30");
						remaindTime.add(tomrrow+" 18:30");
					}
			break;
			case 4:if(whereEating.equals("饭前")) {
						remaindTime.add(tomrrow+" 07:30");
						remaindTime.add(tomrrow+" 11:30");
						remaindTime.add(tomrrow+" 15:30");
						remaindTime.add(tomrrow+" 18:30");
				   }else if(whereEating.equals("饭后")){
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
				if(phone!=null) {
					data.put("phone", phone);
					//通过电话号码查询userId
					//LscExchangeDb lsc = new LscExchangeDb();
					lsc.setData(data);
					lsc.setSqlPath("remiand/QryUserIdByPhone");
					List<Map<String,Object>> l = exchangeDbService.selectDb(lsc);
					data.put("userId",l.get(0).get("userId"));
				}	
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
				List<Integer> l = new ArrayList<Integer>();
				String rt = remaindTime.get(k);
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
						 boxSerialList = exchangeDbService.selectDbNoParam(lsc);
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
					 for(int n=1;n<num;n++) {
						 System.out.println(l.contains(n));
						 if(!l.contains(n)) {
							 data.put("boxId", n);    
							 break;
						 }
					 }
					      
				}
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
