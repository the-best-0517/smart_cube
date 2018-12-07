package shiro_test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class JdbcReamTest {
	DruidDataSource dataSource = new DruidDataSource();
	{
		dataSource.setUrl("jdbc:mysql://localhost:3306/smart_cube?useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
	}
	@Test
	public void testAuthentication() {
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);  //Ȩ�޿��� 
		
		String sql = "select pwd as password from t_user where user_name = ?";
		jdbcRealm.setAuthenticationQuery(sql);
		//1.����securityManager����
		DefaultSecurityManager dsm = new DefaultSecurityManager();
		dsm.setRealm(jdbcRealm);
		//2.������֤����
		SecurityUtils.setSecurityManager(dsm);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("lsc","123");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());  //��֤
		
		//subject.checkRoles("admin");	//��Ȩ
		
	//	subject.checkPermission("user:delete");   //Ȩ��
	//	subject.logout();
	//	System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
	}
}
