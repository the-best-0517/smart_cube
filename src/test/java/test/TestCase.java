package test;

public class TestCase {
		public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			Class c1 = foo.class;
			foo f = new foo();
			Class c2 = f.getClass();
			System.out.println(c1==c2);
			Class c3 = Class.forName("44");
			foo f2 = (foo)c1.newInstance();
			//c1c2c3统称为foo的类类型 classType
		}
	
}
class foo {}
