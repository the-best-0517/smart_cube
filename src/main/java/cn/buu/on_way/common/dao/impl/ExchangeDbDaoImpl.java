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
	 * ������� dao
	 */
	public int saveDb(Map<String,Object> map,String sql) {
		int rows =0;
		try {
			/**����ֵ����*/			
			List<String> list= handleSql(sql);
			sql = list.get(list.size()-1);
			list.remove(list.size()-1);   //���д�ֵ���Ƶ�list
			Object[] obj = new Object[list.size()] ;		//ͨ��list  ���� Object  ����
			for(int i=0;i<list.size();i++) {
				obj[i] = map.get(list.get(i));
			}
			rows = jt.update(sql,obj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new  RuntimeException("sqlִ��ʧ��");
		}		
		return rows;
	}

	/**�����û���sql Ϊ�˷��㴫ֵ*/
	private static List<String> handleSql(String sql) {
		List<String> list = new ArrayList<String>();
		String[] sqlss = sql.split(",");         //ͨ�� �� ��ȡ�ַ���
		for(int j=0;j<sqlss.length;j++) {		//�����ַ�������
			String[] sqls = sqlss[j].split("="); //ͨ�� = ��ȡ�ַ��� �������������滻����
			for(int i=0;i<sqls.length;i++) {
				int s = sqls[i].indexOf("{");		
				if(s>0) {							//�ҵ� ���� { ���ַ�
					String s1 = sqls[i].substring(sqls[i].indexOf("{")); //��ֵ���ʽ		
					String s2 = s1.substring(0, s1.indexOf("}"));						
					String coumlon = s2.substring(1);					// �ҵ����ʽ��  ��ֵ��								
					list.add(coumlon);									//���浽list
					s2 = "#\\"+s2+"\\}";								//ƴ������
					String newsql = sql.replaceAll(s2, "?");			//�滻��ֵ���ʽΪ ��
					sql = newsql;
			}
			}
		}
		list.add(sql);
		return list;		
	}
	/**
	 * ɾ������ dao
	 */
	public int deleteDb(Map<String, Object> data, String sql) {
		int rows = 0;
		Object[] obj =null;
		/**����ֵ����*/	
		try {
		List<String> list= handleSql(sql);
		sql = list.get(list.size()-1);
		list.remove(list.size()-1);   //���д�ֵ���Ƶ�list
		obj = new Object[list.size()] ;		//ͨ��list  ���� Object  ����
		for(int i=0;i<list.size();i++) {
			obj[i] = data.get(list.get(i));
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("sql����ʧ��");
		}
		try {
			jt.update(sql, obj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ɾ��ʧ��");
		}
		return rows;
	}
	/**
	 * ��ѯ���� dao
	 * return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> selectDb(Map<String, Object> data, String sql) {
		List<Map<String,Object>> result = null;
		Object[] obj =null;
		/**����ֵ����*/	
		try {
		List<String> list= handleSql(sql);
		sql = list.get(list.size()-1);			//����� ��sql
		list.remove(list.size()-1);   			//���д�ֵ���Ƶ�list
		obj = new Object[list.size()] ;			//ͨ��list  ���� Object  ����
		for(int i=0;i<list.size();i++) {
			obj[i] = data.get(list.get(i));
		}
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("sql����ʧ��");
		}
		try {
			result = jt.queryForList(sql, obj);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("��ѯʧ��");
		}
		return result;
		
	}
	/**
	 * ��ѯ���� �޲���  dao
	 * param 	 sql   sql���
	 * return	 list  ��ѯ���
	 * author    lsc
	 */
	public List<Map<String, Object>> selectDbNoParam(String sql) {
		List<Map<String,Object>> list = null;
		try {
			list = jt.queryForList(sql);
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("�޲β�ѯʧ��");
		}
		return list;
	}
}
