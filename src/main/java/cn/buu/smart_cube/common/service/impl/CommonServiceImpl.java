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
	public long getOnlyKey() {
		long key = 0;
		try {
			long l = System.currentTimeMillis();
			//new日期对象
			Date date = new Date(l);
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
			String k = sdf.format(date)+(int)((Math.random()*9+1)*1000)+"";
			key = Long.parseLong(k);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return key;
	}

}
