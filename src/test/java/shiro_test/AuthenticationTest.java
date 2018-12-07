package shiro_test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
	SimpleAccountRealm sar = new SimpleAccountRealm();
	@Before
	public void init() {
		sar.addAccount("lsc","123","admin");
	}
	     CustomRealm customRealm = new CustomRealm();//自定义Realm
	@Test
	public void testAuthentication() {
		
		IniRealm iniReam = new IniRealm("classpath:user.xml");
		
		//1.构建securityManager环境
		DefaultSecurityManager dsm = new DefaultSecurityManager();
		//dsm.setRealm(iniReam);
					// 自定义Realm  
		dsm.setRealm(customRealm);
		
		//加密
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("md5");
		hcm.setHashIterations(1);               //加密次数
		customRealm.setCredentialsMatcher(hcm);
		
		
		//2.主体认证请求
		SecurityUtils.setSecurityManager(dsm);
		Subject subject = SecurityUtils.getSubject();
		
		UsernamePasswordToken token = new UsernamePasswordToken("lsc","123");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());  //认证
		
		subject.checkRoles("admin");	//授权
		
		subject.checkPermission("user:delete");   //权限
	//	subject.logout();
	//	System.out.println("isAuthenticated:"+subject.isAuthenticated());
		
	}
}
