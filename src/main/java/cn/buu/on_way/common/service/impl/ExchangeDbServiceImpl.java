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
	 * ���浽���ݿ� jt 
	 * lsc
	 */
	public int saveDb(LscExchangeDb lsc) {
		int rows = 0;
		/**��ȡsql*/
		String sql = readSql(lsc);
				try {
					rows = exchangeDbDaoImpl.saveDb(lsc.getData(),sql);
				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException("sqlִ��ʧ�ܣ�");
				}finally {
					if(rows==1) {
						System.out.println("sqlִ�гɹ���");
					}
				}
					
		return rows;		
	}
	/**
	 * ��ȡSQL�ļ�Ϊ�ַ���
	 * */
	private String readSql(LscExchangeDb lsc) {
		//sql�ļ�·��
		String sqlPath = "/sql/"+lsc.getSqlPath()+".sql.ftl";
		String sql = null;
		//����·����ȡ�ļ�����		
		try {
			//���ļ���ַ�ж�ȡ���ݵ�������
			//1����������
			Properties prop = new Properties();  
			InputStream is = this.getClass().getResourceAsStream(sqlPath);
			//�ȶ���һ���ֽ�����������
			byte[] b = new byte[10000];//�����е����ݶ�ȡ������ֽڵ���
			//����һ��int�洢ÿ�ζ�ȡ��������
			int i = 0;
			//����һ����¼�����ı���
			int index = 0;
			//ѭ����ȡÿ������
			while((i=is.read())!=-1){//�Ѷ�ȡ�����ݷŵ�i��
				b[index]=(byte) i;
				index++;
			}
			//���ֽ�����ת���ַ���
			 sql = new String(b,0,index);
			
			//��������mapping  
		//	String beMapping = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
		//			"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + 
		//			"<mapper namespace=\"cn.buu.on_way.common.dao.ExchangeDbDao\">    	\r\n" + 
		//			"    	<insert id=\"saveDb\" parameterType=\"map\">\r\n";
		//	String afMapping = "\r\n</insert>" + 
		//			"</mapper>";
		//	String mapping = beMapping+sql+afMapping;
		//	System.out.println("mapping:"+mapping);
			// д��Mapping�ļ� /on_way/src/main/resources/mapper/TempMapping.xml
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
			//�ر���
			is.close();
		//	out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException ("ϵͳǿ�ƽ�������⣺�ļ�û���ҵ�");					
		} catch (IOException e) {
			//�ļ���д�쳣
			e.printStackTrace();
			throw new RuntimeException ("�ļ���д�쳣");	
		}
		return sql;
	}
	/**
	 * ɾ������ 
	 * ��lsc��
	 */
	public int deleteDb(LscExchangeDb lsc) {
		int rows = 0;
		String sql = readSql(lsc);
		try {
			rows = exchangeDbDaoImpl.deleteDb(lsc.getData(),sql);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ɾ��ʧ�ܣ�");
		}
		return rows;
	}
	/**
	 * ��ѯ����  
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
			throw new RuntimeException("��ѯʧ�ܣ�");
		}
		return list;
	}
	/**
	 * ��ѯ����  �޲���
	 */
	public List<Map<String, Object>> selectDbNoParam(LscExchangeDb lsc) {
		List<Map<String, Object>> list = null;
		String sql = readSql(lsc);
		try {
			 list = exchangeDbDaoImpl.selectDbNoParam(sql);
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("�޲β�ѯʧ�ܣ�");
			}
			return list;
	}
		
}
