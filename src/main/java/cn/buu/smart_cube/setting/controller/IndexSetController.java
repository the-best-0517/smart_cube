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
		
		
		@RequestMapping("/showAllWriting")
		@ResponseBody
		public JsonResult showAllWriting() {
			System.out.println("showAllWriting");
			hanldDiff();
			
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setSqlPath("setting/QryAllWriting");
			try {
				List<Map<String,Object>> list = exchangeDbServiceImpl.selectDbNoParam(lsc);
				return new JsonResult(list);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
			
		}
		
		
		/**
		 * 通过id查询文章信息
		 * @param id
		 * @return
		 */
		@RequestMapping("/showEditWriting")
		@ResponseBody
		public JsonResult showEditWriting(String id) {
			System.out.println("showEditWriting");
			hanldDiff();
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("id",id);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("setting/QryWritingById");
			try {
				List<Map<String,Object>> list = exchangeDbServiceImpl.selectDb(lsc);
				return new JsonResult(list);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}			
		}
		/**
		 * 保存文章
		 * @param title
		 * @param time
		 * @param text
		 * @return
		 */
		@RequestMapping("/insertWriting")
		@ResponseBody
		public JsonResult insertWriting(String title,String time,String text,String writingId) {
			System.out.println("insertWriting");
			hanldDiff();
			long key;
			if(writingId==null||writingId.length()==0) {
				key = commonServiceImpl.getOnlyKey();
			}else {
				key = Long.parseLong(writingId);
			}
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("writingId", key);
			data.put("writingTitle",title);
		//	data.put("time", time);
			data.put("writingDesc", text);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("setting/insertWriting");
			try {
				exchangeDbServiceImpl.saveDb(lsc);
				return new JsonResult();
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}
						
		}
		/**
		 * 根据id查询当条公告信息
		 * @param id
		 * @return
		 */
		@RequestMapping("/showEditNotic")
		@ResponseBody
		public JsonResult showEditNotic(String id) {
			System.out.println("showEditNotic");
			hanldDiff();
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("id",id);
			LscExchangeDb lsc = new LscExchangeDb();
			lsc.setData(data);
			lsc.setSqlPath("setting/QryNoticById");
			try {
				List<Map<String,Object>> list = exchangeDbServiceImpl.selectDb(lsc);
				return new JsonResult(list);
			}catch(Exception e) {
				e.printStackTrace();
				return new JsonResult("error");
			}			
		}
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
		public JsonResult insertNotic(String title,String time,String text,String noticId) {
			long key;
			if(noticId.length()==0||noticId==null) {
				key = commonServiceImpl.getOnlyKey();
			}else {
				key = Long.parseLong(noticId);
			}
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
