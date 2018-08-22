package cn.buu.smart_cube.common.service.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
import cn.buu.smart_cube.common.service.CommonService;
@Service
public class CommonServiceImpl implements CommonService{
	@Resource
	private ExchangeDbService exchangeDbService;
	public int getOnlyKey() {
		int key = 0;
		try {
			LscExchangeDb lsc = new LscExchangeDb();
			//lsc.setData(data);
			lsc.setSqlPath("test/getMySqlTime");
			List<Map<String,Object>> list = exchangeDbService.selectDbNoParam(lsc);
			Date sqldate = (Date) list.get(0).get("sqldate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			key = Integer.parseInt(sdf.format(sqldate))+(int)((Math.random()*9+1)*10000);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return key;
	}

}
