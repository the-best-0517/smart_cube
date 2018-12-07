package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ClassUtil {
		public static void getClassMsg(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			//通过对象获取到类类型
			Class c = obj.getClass();
			//c.getName();
			System.out.println("类的名称："+c.getName());
			
/*			//利用反射操作执行方法
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
			
			//获取方法
			Method[] mt = c.getMethods();
			for(Method m : mt) {
				
				
				//返回类型的  类类型
				Class type = m.getReturnType();
				
				System.out.print(type.getName() +" ");
				System.out.print(m.getName()+"(");
				//参数的类类型
				Parameter[] p = m.getParameters();
				for(Parameter pp : p) {
					System.out.print(pp.getName()+", " );
				}
				System.out.println(")");
			}
			
			
			//获得成员变量
			java.lang.reflect.Field[] f = c.getFields();
			for(int i=0;i<f.length;i++) {
				System.out.println("f[i].getType().getName():"+f[i].getType().getName());
			}
			
			
			//获取构造方法的信息
			Constructor[] cc = c.getConstructors();
			for(Constructor con : cc) {
				con.getName();
				con.getParameterTypes();//参数列表的类类型
			}
		}
}
///想获得类的信息，首先要获得类的类类型