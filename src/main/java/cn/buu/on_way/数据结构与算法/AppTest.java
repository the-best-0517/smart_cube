package cn.buu.on_way.���ݽṹ���㷨;

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
        // �õ������
        Class userCla = (Class) obj.getClass();
        /* �õ����е��������Լ��� */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            System.out.println(f.getName());
            f.setAccessible(true); // ����Щ�����ǿ��Է��ʵ�
            Object val = new Object();
            try {
                val = f.get(obj);
                // �õ������Ե�ֵ
                map.put(f.getName(), val);// ���ü�ֵ
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            /*
             * String type = f.getType().toString();//�õ������Ե����� if
             * (type.endsWith("String")) {
             * System.out.println(f.getType()+"\t��String"); f.set(obj,"12") ;
             * //��������ֵ }else if(type.endsWith("int") ||
             * type.endsWith("Integer")){
             * System.out.println(f.getType()+"\t��int"); f.set(obj,12) ; //��������ֵ
             * }else{ System.out.println(f.getType()+"\t"); }
             */

        }
        System.out.println("������������м�ֵ==����==" + map.toString());
        return map;
    }


}