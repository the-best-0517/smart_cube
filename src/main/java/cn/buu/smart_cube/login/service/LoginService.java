package cn.buu.smart_cube.login.service;

import java.util.List;
import java.util.Map;

public interface LoginService {
	/**
	 * ��ѯ�����û���
	 * @return
	 */
	List<Map<String, Object>> findAllUser();

}
