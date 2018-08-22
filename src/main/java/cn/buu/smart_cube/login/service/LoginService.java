package cn.buu.smart_cube.login.service;

import java.util.List;
import java.util.Map;

public interface LoginService {
	/**
	 * 查询所有用户名
	 * @return
	 */
	List<Map<String, Object>> findAllUser();

}
