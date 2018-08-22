package cn.buu.on_way.common.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.buu.on_way.common.dao.ExchangeDbDao;
@Repository
public class ExchangeDbDaoImpl implements ExchangeDbDao{
	@Resource
	private JdbcTemplate jt;
	/**
	 * 保存操作 dao
	 */
	public int saveDb(Map<String,Object> map,String sql) {
		int rows =0;
		try {
			/**处理传值问题*/			
			List<String> list= handleSql(sql);
			sql = list.get(list.size()-1);
			list.remove(list.size()-1);   //含有传值名称的list
			Object[] obj = new Object[list.size()] ;		//通过list  制作 Object  数组
			for(int i=0;i<list.size();i++) {
				obj[i] = map.get(list.get(i));
			}
			rows = jt.update(sql,obj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new  RuntimeException("sql执行失败");
		}		
		return rows;
	}

	/**处理用户的sql 为了方便传值*/
	private static List<String> handleSql(String sql) {
		List<String> list = new ArrayList<String>();
		String[] sqlss = sql.split(",");         //通过 ， 截取字符串
		for(int j=0;j<sqlss.length;j++) {		//遍历字符串数组
			String[] sqls = sqlss[j].split("="); //通过 = 截取字符串 避免多参数不能替换问题
			for(int i=0;i<sqls.length;i++) {
				int s = sqls[i].indexOf("{");		
				if(s>0) {							//找到 含有 { 的字符
					String s1 = sqls[i].substring(sqls[i].indexOf("{")); //传值表达式		
					String s2 = s1.substring(0, s1.indexOf("}"));						
					String coumlon = s2.substring(1);					// 找到表达式中  传值名								
					list.add(coumlon);									//保存到list
					s2 = "#\\"+s2+"\\}";								//拼接正则
					String newsql = sql.replaceAll(s2, "?");			//替换传值表达式为 ？
					sql = newsql;
			}
			}
		}
		list.add(sql);
		return list;		
	}
	/**
	 * 删除操作 dao
	 */
	public int deleteDb(Map<String, Object> data, String sql) {
		int rows = 0;
		Object[] obj =null;
		/**处理传值问题*/	
		try {
		List<String> list= handleSql(sql);
		sql = list.get(list.size()-1);
		list.remove(list.size()-1);   //含有传值名称的list
		obj = new Object[list.size()] ;		//通过list  制作 Object  数组
		for(int i=0;i<list.size();i++) {
			obj[i] = data.get(list.get(i));
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("sql解析失败");
		}
		try {
			jt.update(sql, obj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除失败");
		}
		return rows;
	}
	/**
	 * 查询操作 dao
	 * return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> selectDb(Map<String, Object> data, String sql) {
		List<Map<String,Object>> result = null;
		Object[] obj =null;
		/**处理传值问题*/	
		try {
		List<String> list= handleSql(sql);
		sql = list.get(list.size()-1);			//处理后 的sql
		list.remove(list.size()-1);   			//含有传值名称的list
		obj = new Object[list.size()] ;			//通过list  制作 Object  数组
		for(int i=0;i<list.size();i++) {
			obj[i] = data.get(list.get(i));
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("sql解析失败");
		}
		try {
			result = jt.queryForList(sql, obj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败");
		}
		return result;
		
	}
	/**
	 * 查询数据 无参数  dao
	 * param 	 sql   sql语句
	 * return	 list  查询语句
	 * author    lsc
	 */
	public List<Map<String, Object>> selectDbNoParam(String sql) {
		List<Map<String,Object>> list = null;
		try {
			list = jt.queryForList(sql);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("无参查询失败");
		}
		return list;
	}
}
