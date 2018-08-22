package cn.buu.on_way.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


public interface ExchangeDbDao {
		/**
		 * 增，删，改  数据库操作
		 * @param map	Map数据，所需参数在此Map中存在
		 * @param sql	sql语句
		 * @return		成功条数  0为失败
		 */
		int saveDb(Map<String,Object> map,String sql);
		/**
		 * 数据库中 删除数据
		 * @param map
		 * @param sql
		 * @return
		 */
		int deleteDb(Map<String,Object> map,String sql);
		/**
		 * 查询操作
		 * @param map 	Map数据，所需参数在此Map中存在
		 * @param sql	sql语句
		 * @return list	每一条数据是一个list,每一个字段为一个map
		 */
		List<Map<String,Object>> selectDb(Map<String, Object> map, String sql);
		List<Map<String, Object>> selectDbNoParam(String sql);
}
