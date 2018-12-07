package cn.buu.seckill.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.buu.seckill.dto.Exposer;
import cn.buu.seckill.dto.SeckillExcution;
import cn.buu.seckill.entity.Seckill;
import cn.buu.seckill.exception.RepeatKillException;
import cn.buu.seckill.exception.SeckillCloseException;
import cn.buu.seckill.service.SeckillService;
import cn.buu.smart_cube.common.web.JsonResult;

@Controller
@RequestMapping("/seckill")//url:/模块/资源/{id}/细分
public class SeckillController {
	
	@Resource
	private SeckillService ss;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = ss.getSeckillList();
		model.addAttribute("list", list);
		return "list";
	}
	@RequestMapping(value="/{seckillId}/detail",method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId")Long seckillId,Model model) {
		if(seckillId==null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = ss.getSeckillById(seckillId);
		if(seckill==null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	//ajax 接口  return json
	@RequestMapping(value="/{seckillId}/exposer",
					method = RequestMethod.POST,
					produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public JsonResult exposer(@PathVariable("seckillId")Long seckillId) {
		Exposer e = ss.exportSeckillUrl(seckillId);
		return new JsonResult(e);
		
	}
	@RequestMapping(value="/{seckillId}/{md5}/excute",
			method = RequestMethod.POST,
			produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public JsonResult excute(@PathVariable("seckillId")Long seckillId,
							 @PathVariable("md5")String md5,
							@CookieValue(value="killPhone",required=false)Long phone) {
		if(phone==null) {
			return new JsonResult("未注册");
		}
		try {
			SeckillExcution e = ss.excuteSeckill(seckillId, phone, md5);
			return new JsonResult(e);
		}catch(RepeatKillException e) {
			return new JsonResult("重复");
		}catch(SeckillCloseException e) {
			return new JsonResult("已关闭");
		}catch(Exception e) {
			return new JsonResult("系统异常");
		}
	
	}
	
	@RequestMapping(value="/time/now",
			method = RequestMethod.GET,
			produces= {"application/json;charset=UTF-8"})
	@ResponseBody
	public JsonResult time() {
		Date date = new Date();
		return new JsonResult(date.getTime());
		
	}
}
