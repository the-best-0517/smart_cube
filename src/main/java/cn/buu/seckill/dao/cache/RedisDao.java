package cn.buu.seckill.dao.cache;


import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import cn.buu.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;

public class RedisDao {
	
	//private JedisPool jedisPool;
	private Jedis jedis;
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	public RedisDao(String ip,int port,String pwd) {
			jedis = new Jedis(ip,port);
			//jedisPool = new JedisPool(ip,port);
			jedis.auth(pwd);
	}
	public Seckill getSeckill(long seckillId) {
		//redis操作逻辑
		try {
			try {
				String key = "seckill:"+seckillId;
				//并没有实现内部序列号操作
				//get->byte[]->反序列化->Object
				//采用自定义序列化   protostuff : pojo
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null) {
					//缓存中获取到对象
					//空对象
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					//seckill 反序列化
					return seckill;
				}
			}finally {
				jedis.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String putSeckill(Seckill seckill) {
		//set -> Object ->序列化 -> byte[] 
		String res = null;
		try {
			try {
				String key = "seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,schema,
					LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout  = 60*60;
				res = jedis.setex(key.getBytes(),timeout,bytes);
				System.out.println("res:"+res);
			}finally {
				jedis.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
