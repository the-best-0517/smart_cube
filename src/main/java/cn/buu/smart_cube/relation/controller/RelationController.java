package cn.buu.smart_cube.relation.controller;

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
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/relation")
public class RelationController extends CommonController{
		@Resource
		private ExchangeDbService exchangeDbService;
		
		
		@RequestMapping("/showLinkMan")
		@ResponseBody
		public JsonResult showLinkMan(HttpSession session) {
			List<Map<String,Object>> list = null;
			Object userId = session.getAttribute("userId");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("userId", userId==null?123:userId);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setSqlPath("relation/showLinkMan");
			lsc.setData(data);
			try {
				list = exchangeDbService.selectDb(lsc);
				return new JsonResult(list);	
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");	
			}		
		}
		/**
		 * 保存联系人
		 * @param userName
		 * @param phone
		 * @param vCode
		 * @param relationShip
		 * @param session
		 * @return
		 */
		@RequestMapping("/saveLinkMan")
		@ResponseBody
		public JsonResult saveLinkMan(String userName,String phone,String vCode,String relationShip,HttpSession session) {
			System.out.println("saveLinkMan");
			hanldDiff();
			if(!"1234".equals(vCode)) {
				return new JsonResult("验证码错误");
			}
			long linkId =(long)(Math.random()*10000000);
			Object userId = session.getAttribute("userId");
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("linkId", linkId);
			data.put("userId", userId);
			data.put("userName", userName);
			data.put("phone",phone );
			data.put("relationShip",relationShip);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("relation/saveLinkMan");
			try {
				exchangeDbService.saveDb(lsc);
				return new JsonResult();
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
			
			
		}
}
