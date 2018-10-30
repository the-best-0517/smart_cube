package cn.buu.smart_cube.ajaxDb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.impl.ExchangeDbServiceImpl;

@Controller
public class SendHttpController {
		@Resource
		private ExchangeDbServiceImpl exchangeDbServiceImpl;
	
		@RequestMapping("/sendHttpRequest")
		@ResponseBody
		public String sendHttpRequest(HttpSession session) {
			System.out.println("sendHttpRequest");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("userId",session.getAttribute("userId")==null?123:session.getAttribute("userId"));
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("ajaxDb/QryMaxNum");
			List<Map<String,Object>> maxBoxNum = exchangeDbServiceImpl.selectDb(lsc);
			int bn = Integer.parseInt(maxBoxNum.get(0).get("boxNum").toString())+1;
			String boxSerial = maxBoxNum.get(0).get("boxSerial").toString();
			String boxNum = ""+bn;
			if(bn<10) {
				boxNum="0"+bn;
			}
			System.out.println("boxNum:"+boxNum);
			return boxNum;
			
		}
}
