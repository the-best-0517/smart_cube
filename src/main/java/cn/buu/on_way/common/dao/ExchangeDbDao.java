package cn.buu.on_way.common.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


public interface ExchangeDbDao {
		/**
		 * ����ɾ����  ���ݿ����
		 * @param map	Map���ݣ���������ڴ�Map�д���
		 * @param sql	sql���
		 * @return		�ɹ�����  0Ϊʧ��
		 */
		int saveDb(Map<String,Object> map,String sql);
		/**
		 * ���ݿ��� ɾ������
		 * @param map
		 * @param sql
		 * @return
		 */
		int deleteDb(Map<String,Object> map,String sql);
		/**
		 * ��ѯ����
		 * @param map 	Map���ݣ���������ڴ�Map�д���
		 * @param sql	sql���
		 * @return list	ÿһ��������һ��list,ÿһ���ֶ�Ϊһ��map
		 */
		List<Map<String,Object>> selectDb(Map<String, Object> map, String sql);
		List<Map<String, Object>> selectDbNoParam(String sql);
}
