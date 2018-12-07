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
 * �Զ���realm
 * @author ABC
 *
 */
public class CustomRealm extends AuthorizingRealm{
	{
		super.setName("customRealm");
	}
	/**
	 * ��Ȩ
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//1.��ȡ�û�
		//2.ͨ���û���ȡ��ɫ��Ȩ������
		return null;
	}

	/**
	 * ��֤
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		//1.�����崫��������֤��Ϣ�л�ȡ�û���
		
		//2.ͨ���û�����������
		String pwd = "";
		//3.�ȶ���Ϣ
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
