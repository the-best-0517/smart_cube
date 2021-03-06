package cn.buu.smart_cube.infocard;

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
/**
 * 
 * @author ABC
 *
 */
@Controller
@RequestMapping("/infoCard")
public class InfoCardController extends CommonController{
	@Resource
	private ExchangeDbService exchangeDbService;
	/**
	 * @author 15874
	 * @param session
	 * @return
	 */
	@RequestMapping("/showInfoCard")
	@ResponseBody
	public JsonResult showInfoCard(HttpSession session) {
		System.out.println("showInfoCard");
		hanldDiff();
		Object userId = session.getAttribute("userId");
		List<Map<String,Object>> list = null;
		Map<String,Object> data =new HashMap<String,Object>();
		data.put("userId", userId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("infoCard/showInfoCard");
		try {
			list = exchangeDbService.selectDb(lsc);
			return new JsonResult(list);
		}catch(Exception e) {
			e.printStackTrace();	
			return new JsonResult("error");
		}	
	}
	
	@RequestMapping("/saveInfoCard")
	@ResponseBody
	public JsonResult saveInfoCard(HttpSession session,String name,String sex,
						String bloodType,String birthday,
						String allergyBill,String passIlls,String emergencyNum) {
		System.out.println("saveInfoCard");
		hanldDiff();
		Object usrId = session.getAttribute("userId");
		Map<String,Object> data = new HashMap<String, Object>(100);
		data.put("name", name);
		String man = "男";
		if(man.equals(sex)) {
			data.put("sex",1 );
		}else {
			data.put("sex",0 );
		}
		
		data.put("bloodType", bloodType);
		data.put("birthday",birthday );
		data.put("allergyBill", allergyBill);
		data.put("passIlls", passIlls);
		data.put("emergencyNum",emergencyNum);
		data.put("userId", usrId);
		LscExchangeDb lsc = new LscExchangeDb();
		lsc.setData(data);
		lsc.setSqlPath("infoCard/replaceInfoCard");
		try {
			exchangeDbService.saveDb(lsc);
			return new JsonResult();
		}catch(Exception e) {
			e.printStackTrace();
			return new JsonResult(e);
		}
		
	}
}
