package cn.buu.on_way.数据结构与算法;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AppTest {
    //private NodeClass nodeClass;

    public static String  hehe = "hehe";

    public String xixi = "xixi";


    public static void main(String[] args) {
    	
    	getKeyAndValue(new AppTest());
        /*Field[] fields = AppTest.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
               // if(field.getType().toString().endsWith("java.lang.String") && Modifier.isStatic(field.getModifiers()))
                    System.out.println(field.getName() + " , " + field.get(AppTest.class));
          }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    
    
    
    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            System.out.println(f.getName());
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            /*
             * String type = f.getType().toString();//得到此属性的类型 if
             * (type.endsWith("String")) {
             * System.out.println(f.getType()+"\t是String"); f.set(obj,"12") ;
             * //给属性设值 }else if(type.endsWith("int") ||
             * type.endsWith("Integer")){
             * System.out.println(f.getType()+"\t是int"); f.set(obj,12) ; //给属性设值
             * }else{ System.out.println(f.getType()+"\t"); }
             */

        }
        System.out.println("单个对象的所有键值==反射==" + map.toString());
        return map;
    }


}