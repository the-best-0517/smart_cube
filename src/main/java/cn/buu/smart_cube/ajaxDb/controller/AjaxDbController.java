package cn.buu.smart_cube.ajaxDb.controller;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.impl.ExchangeDbServiceImpl;
 
@Controller
public class AjaxDbController  {
	@Resource
	private ExchangeDbServiceImpl exchangeDbServiceImpl;
	
	private static final long serialVersionUID = 1L;       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
	}
	@RequestMapping("/ServletForPOSTMethod")
	@ResponseBody
	protected void doPost(HttpServletRequest request, HttpServletResponse response,HttpSession session,String pwd) throws ServletException, IOException {
		Map<String,Object> data = new HashMap<String,Object>();
		 pwd= request.getParameter("pwd");
		System.out.println("pwd from POST method: " + pwd );
		if(pwd.length()==0) {
			return;
		}
		if(pwd.startsWith("SOS")) {
			//发短信
			
		}
		if(pwd.startsWith("C")) {
			System.out.println("C....");
			System.out.println("pinse:"+pwd.indexOf(":"));
			if(pwd.indexOf(":")!=-1) {
				System.out.println("c:c:c:c");
				return;
			}
			String num = pwd.charAt(1)+""+pwd.charAt(2);
			System.out.println("num:"+num);
			int n =pwd.lastIndexOf("0");
			System.out.println("n0:"+n);
			n = (Integer.parseInt(num)-1)*3+(n-3+1);
			System.out.println("n2:"+n);
			data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
			data.put("boxId", n);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("remiand/updateIfEating");
			try {
				exchangeDbServiceImpl.saveDb(lsc);
				//通知家长
				lsc.setSqlPath("remiand/QryFamilyUserId");
				List<Map<String,Object>> fuList = exchangeDbServiceImpl.selectDb(lsc);
				for(int i=0;i<fuList.size();i++) {
					data.put("fuId",fuList.get(i).get("userId"));
					data.put("informDesc","您的家属已按时吃药！");
					lsc.setData(data);
					lsc.setSqlPath("remiand/makeNotic");
					exchangeDbServiceImpl.saveDb(lsc);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		if(pwd.startsWith(",Z")) {
			//检测副盒是否都在		
			List<String> l = new ArrayList<String>();
			LscExchangeDb lsc = new LscExchangeDb();
			data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
			lsc.setData(data);
			lsc.setSqlPath("ajaxDb/QryDbboxNum");
			List<Map<String,Object>> list = exchangeDbServiceImpl.selectDb(lsc);
			String[] ps= pwd.replace("Z", "").split(",");
			for(int i=1;i<ps.length;i++) {
				Integer boxNum = Integer.parseInt(ps[i]);
				System.out.println("boxNum:"+boxNum);
				l.add(boxNum.toString());		
			}
			System.out.println("l:"+l);
			for(int i=0;i<list.size();i++) {
				System.out.println(list.get(i).get("boxNum"));
				if(!l.contains(list.get(i).get("boxNum").toString())) {
					data.put("boxNum", list.get(i).get("boxNum"));
					data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
					lsc.setData(data);
					lsc.setSqlPath("ajaxDb/deleteBox");
					System.out.println("删除");
					exchangeDbServiceImpl.deleteDb(lsc);
				}
			}
		}
		if(pwd.startsWith("N")) {
			System.out.println("N...");
			int bn = 0;
			data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
			System.out.println("pwd.substring(0):"+pwd.substring(3));
			data.put("boxSerial", pwd.substring(3));
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			/**检查序列号是否存在*/
			lsc.setSqlPath("ajaxDb/checkSerialIfExit");
			List<Map<String,Object>>  list = exchangeDbServiceImpl.selectDb(lsc);
			if(list.size()!=0) {
				System.out.println("系列号存在");
				return;
			}
			lsc.setSqlPath("ajaxDb/QryMaxNum");
			List<Map<String,Object>> maxBoxNum = exchangeDbServiceImpl.selectDb(lsc);
			System.out.println("maxBoxNum:"+maxBoxNum);
			if(maxBoxNum.isEmpty()||maxBoxNum.size()==0) {
				bn = 1;
			}else {
				bn = Integer.parseInt(maxBoxNum.get(0).get("boxNum").toString())+1;
			}			 
			if(bn<10) {
				data.put("boxNum", "0"+bn);
			}else {
				data.put("boxNum",bn);
			}
			lsc.setData(data);
			lsc.setSqlPath("remiand/insertBoxSerial");
			try {
				exchangeDbServiceImpl.saveDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("box save ok");
		}
	}
}
