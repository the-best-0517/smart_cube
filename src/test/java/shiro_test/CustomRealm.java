package shiro_test;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
/**
 * 自定义realm
 * @author ABC
 *
 */
public class CustomRealm extends AuthorizingRealm{
	{
		super.setName("customRealm");
	}
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//1.获取用户
		//2.通过用户获取角色，权限数据
		return null;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//1.从主体传过来的认证信息中获取用户名
		
		//2.通过用户名查找密码
		String pwd = "";
		//3.比对信息
		SimpleAuthenticationInfo simpleAuthenticationInfo
		  		= new SimpleAuthenticationInfo("lsc",pwd,"customRealm");
		simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
		return simpleAuthenticationInfo;
	}
  public static void main(String[] args) {
	  	Md5Hash m5 = new Md5Hash("1233","salt");
	  	System.out.println("m5:"+m5);
}
}
