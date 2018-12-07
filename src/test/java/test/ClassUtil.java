package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ClassUtil {
		public static void getClassMsg(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			//ͨ�������ȡ��������
			Class c = obj.getClass();
			//c.getName();
			System.out.println("������ƣ�"+c.getName());
			
/*			//���÷������ִ�з���
			try {
				Method m  = c.getMethod("equals");
				m.invoke(obj);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			*/
			
			//��ȡ����
			Method[] mt = c.getMethods();
			for(Method m : mt) {
				
				
				//�������͵�  ������
				Class type = m.getReturnType();
				
				System.out.print(type.getName() +" ");
				System.out.print(m.getName()+"(");
				//������������
				Parameter[] p = m.getParameters();
				for(Parameter pp : p) {
					System.out.print(pp.getName()+", " );
				}
				System.out.println(")");
			}
			
			
			//��ó�Ա����
			java.lang.reflect.Field[] f = c.getFields();
			for(int i=0;i<f.length;i++) {
				System.out.println("f[i].getType().getName():"+f[i].getType().getName());
			}
			
			
			//��ȡ���췽������Ϣ
			Constructor[] cc = c.getConstructors();
			for(Constructor con : cc) {
				con.getName();
				con.getParameterTypes();//�����б��������
			}
		}
}
///���������Ϣ������Ҫ������������