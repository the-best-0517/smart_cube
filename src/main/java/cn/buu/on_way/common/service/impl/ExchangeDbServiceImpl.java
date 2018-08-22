package cn.buu.on_way.common.service.impl;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.buu.on_way.common.dao.impl.ExchangeDbDaoImpl;
import cn.buu.on_way.common.entity.LscExchangeDb;
import cn.buu.on_way.common.service.ExchangeDbService;
@Service
public class ExchangeDbServiceImpl implements ExchangeDbService{
	@Resource  
	private ExchangeDbDaoImpl exchangeDbDaoImpl;
	@Resource
	HttpServletRequest request;//request.getServletContext().getContextPath()
	/**
	 * 保存到数据库 jt 
	 * lsc
	 */
	public int saveDb(LscExchangeDb lsc) {
		int rows = 0;
		/**读取sql*/
		String sql = readSql(lsc);
				try {
					rows = exchangeDbDaoImpl.saveDb(lsc.getData(),sql);
				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException("sql执行失败！");
				}finally {
					if(rows==1) {
						System.out.println("sql执行成功！");
					}
				}
					
		return rows;		
	}
	/**
	 * 读取SQL文件为字符串
	 * */
	private String readSql(LscExchangeDb lsc) {
		//sql文件路径
		String sqlPath = "/sql/"+lsc.getSqlPath()+".sql.ftl";
		String sql = null;
		//根据路径读取文件内容		
		try {
			//从文件地址中读取内容到程序中
			//1、建立连接
			Properties prop = new Properties();  
			InputStream is = this.getClass().getResourceAsStream(sqlPath);
			//先定义一个字节数组存放数据
			byte[] b = new byte[10000];//把所有的数据读取到这个字节当中
			//声明一个int存储每次读取到的数据
			int i = 0;
			//定义一个记录索引的变量
			int index = 0;
			//循环读取每个数据
			while((i=is.read())!=-1){//把读取的数据放到i中
				b[index]=(byte) i;
				index++;
			}
			//把字节数组转成字符串
			 sql = new String(b,0,index);
			
			//生成所需mapping  
		//	String beMapping = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
		//			"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + 
		//			"<mapper namespace=\"cn.buu.on_way.common.dao.ExchangeDbDao\">    	\r\n" + 
		//			"    	<insert id=\"saveDb\" parameterType=\"map\">\r\n";
		//	String afMapping = "\r\n</insert>" + 
		//			"</mapper>";
		//	String mapping = beMapping+sql+afMapping;
		//	System.out.println("mapping:"+mapping);
			// 写入Mapping文件 /on_way/src/main/resources/mapper/TempMapping.xml
			//	File file = new File("../../../../resources/mapper/TempMapping.xml");
			///on_way/src/main/resources/mapper/TempMapping.xml
			// URL resourceURL = ExchangeDbService.class.getResource("/mapper/TempMapping.xml");
		    //    String fileName = resourceURL.getFile();
		     //   System.out.println(fileName);
		//	File jsonFile = ResourceUtils.getFile("classpath:mapper/TempMapping.xml");
			//FileOutputStream out = new FileOutputStream(jsonFile);				
		//	BufferedWriter out = new BufferedWriter (
		//			new OutputStreamWriter (
		//					new FileOutputStream (jsonFile),"UTF-8"));
		//	out.write(mapping);
			//关闭流
			is.close();
		//	out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException ("系统强制解决的问题：文件没有找到");					
		} catch (IOException e) {
			//文件读写异常
			e.printStackTrace();
			throw new RuntimeException ("文件读写异常");	
		}
		return sql;
	}
	/**
	 * 删除数据 
	 * （lsc）
	 */
	public int deleteDb(LscExchangeDb lsc) {
		int rows = 0;
		String sql = readSql(lsc);
		try {
			rows = exchangeDbDaoImpl.deleteDb(lsc.getData(),sql);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("删除失败！");
		}
		return rows;
	}
	/**
	 * 查询数据  
	 * return  List<Map<String,Object>>
	 */
	public List<Map<String, Object>> selectDb(LscExchangeDb lsc) {
		System.out.println("lsc:"+lsc);
		List<Map<String, Object>> list = null;
		String sql = readSql(lsc);
		try {
		 list = exchangeDbDaoImpl.selectDb(lsc.getData(),sql);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("查询失败！");
		}
		return list;
	}
	/**
	 * 查询数据  无参数
	 */
	public List<Map<String, Object>> selectDbNoParam(LscExchangeDb lsc) {
		List<Map<String, Object>> list = null;
		String sql = readSql(lsc);
		try {
			 list = exchangeDbDaoImpl.selectDbNoParam(sql);
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("无参查询失败！");
			}
			return list;
	}
		
}
