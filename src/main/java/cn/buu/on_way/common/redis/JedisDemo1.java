package cn.buu.on_way.common.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis 的测试
 * @author ABC
 *
 */
public class JedisDemo1 {
		@Test
		public void testJedis() {
			//1.设置IP地址和端口
			Jedis jedis = new Jedis("39.105.6.220",6379);
			jedis.auth("twor@$Add");
			//2.保存数据 
			//jedis.set("BoxIndex","8");
			//3.获取数据
			String val = jedis.get("\\xAC\\xED\\x00\\x05t\\x00\\x08BoxIndex");
			System.out.println("val:"+val);
			//4.释放资源
			jedis.close();
			
		}
		
		@Test
		public void testJedis2() {
			//获得连接池对象
			JedisPoolConfig config = new JedisPoolConfig();
			//设置最大连接数
			config.setMaxTotal(30);
			//设置最大空闲连接数
			config.setMaxIdle(10);
			
			//获得连接池   timeout ---> 响应时间
			JedisPool jedisPool = new JedisPool(config, "120.79.10.49", 6379, 5000, "123456");
			
			Jedis jedis = jedisPool.getResource();
			//2.保存数据
			jedis.set("age","22");
			//3.获取数据
			String val = jedis.get("age");
			System.out.println("val:"+val);
			//4.释放资源
			jedis.close();
			jedisPool.close();
			
		}
}
