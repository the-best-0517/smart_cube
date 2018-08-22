package cn.buu.smart_cube.common.contoller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import cn.buu.smart_cube.common.service.impl.CommonServiceImpl;

@Controller
public class CommonController {
	//@Resource
//	private CommonServiceImpl commonService;
	@Resource
	private HttpServletResponse response;
	/*@Resource
	private ExchangeDbService db;
			@RequestMapping("lsc")
			public void lsc() {
				String sqlPath = "";
				Map data =new  HashMap();
				LscExchangeDb lsc = new LscExchangeDb();
				lsc.setData(data);
				lsc.setSqlPath(sqlPath);
				db.saveDb(lsc);
			}*/
	/**
	 * 获取数据库时间+五位随机数
	 * @return
	 */
/*	public int getOnlyKey() {
		int key = 0;
		try {
			key = commonService.getOnlyKey();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return key;		
	}
	*/
		/**
		 * 处理跨域请求的问题
		 */
		public void hanldDiff() {    	        
		    response.setHeader("Access-Control-Allow-Origin","*");   
		    response.setHeader("Access-Control-Allow-Methods","GET,POST");      
		}
}
