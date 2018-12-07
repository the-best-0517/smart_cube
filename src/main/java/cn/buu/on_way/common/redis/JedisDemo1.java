package cn.buu.on_way.common.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis �Ĳ���
 * @author ABC
 *
 */
public class JedisDemo1 {
		@Test
		public void testJedis() {
			//1.����IP��ַ�Ͷ˿�
			Jedis jedis = new Jedis("120.79.10.49",6379);
			jedis.auth("123456");
			//2.��������
			jedis.set("name","lsc1");
			//3.��ȡ����
			String val = jedis.get("name");
			System.out.println("val:"+val);
			//4.�ͷ���Դ
			jedis.close();
			
		}
		
		@Test
		public void testJedis2() {
			//������ӳض���
			JedisPoolConfig config = new JedisPoolConfig();
			//�������������
			config.setMaxTotal(30);
			//����������������
			config.setMaxIdle(10);
			
			//������ӳ�   timeout ---> ��Ӧʱ��
			JedisPool jedisPool = new JedisPool(config, "120.79.10.49", 6379, 5000, "123456");
			
			Jedis jedis = jedisPool.getResource();
			//2.��������
			jedis.set("age","22");
			//3.��ȡ����
			String val = jedis.get("age");
			System.out.println("val:"+val);
			//4.�ͷ���Դ
			jedis.close();
			jedisPool.close();
			
		}
}