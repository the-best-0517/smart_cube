package cn.buu.smart_cube.setting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.impl.ExchangeDbServiceImpl;
import cn.buu.smart_cube.common.contoller.CommonController;
import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/setting")
public class IndexSetController extends CommonController{
		@Resource
		private ExchangeDbServiceImpl exchangeDbServiceImpl;
		@Resource
		private CommonServiceImpl commonServiceImpl;
		/**
		 * 查询所有公告信息
		 * @return
		 */
		@RequestMapping("/showAllNotic")
		@ResponseBody
		public JsonResult showAllNotic() {
			System.out.println("showAllNotic");
			hanldDiff();
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setSqlPath("setting/QryAllNotic");
			try {
				List<Map<String,Object>> list = exchangeDbServiceImpl.selectDbNoParam(lsc);
				return new JsonResult(list);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
		}
			
		/**
		 * 保存公告信息
		 * @param title
		 * @param time
		 * @param text
		 * @return
		 */
		@RequestMapping("/insertNotic")
		@ResponseBody
		public JsonResult insertNotic(String title,String time,String text) {
			long key = commonServiceImpl.getOnlyKey();
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("noticId", key);
			data.put("noticTitle",title);
		//	data.put("time", time);
			data.put("noticDesc", text);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("setting/insertNotic");
			try {
				exchangeDbServiceImpl.saveDb(lsc);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
			return new JsonResult();			
		}
}
