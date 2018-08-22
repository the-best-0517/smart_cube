package cn.buu.on_way.common.service;

import java.util.List;
import java.util.Map;

import cn.buu.on_way.common.entity.LscExchangeDb;


public interface ExchangeDbService {
		/**
		 * 
		 * @param lsc LscExchangeDb对象  用于设置数据和SQLId
		 * @return
		 */
		int saveDb(LscExchangeDb lsc);
		int deleteDb(LscExchangeDb lsc);
		List<Map<String, Object>> selectDb(LscExchangeDb lsc);
		List<Map<String, Object>> selectDbNoParam(LscExchangeDb lsc);
}
