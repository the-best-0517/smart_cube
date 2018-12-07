package test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.buu.seckill.dao.SeckillDao;
import cn.buu.seckill.dao.SuccessKilledDao;
import cn.buu.seckill.dao.cache.RedisDao;
import cn.buu.seckill.entity.Seckill;
import cn.buu.seckill.entity.SuccessKilled;


public class SeckillDaoTest {
//	@Resource
//	private RedisDao redisDao;

	private SeckillDao dao;
	
	private SuccessKilledDao sdao;
	@Before
   	public void init() {
		ClassPathXmlApplicationContext	ctx=new ClassPathXmlApplicationContext(
				"spring-mvc.xml",
				"spring-pool.xml",
				"spring-mybatis.xml",
				"mybatis-config.xml");
      	   // ApplicationContext ac  = new ClassPathXmlApplicationContext(config);
      	    dao = ctx.getBean("seckillDao", SeckillDao.class);
      	  sdao = ctx.getBean("successKilledDao", SuccessKilledDao.class);
   	}							
	@Test
	public void test1() {
		long id = 1;
		Seckill s = dao.queryById(id);
		System.out.println(s);
	}
	@Test
	public void queryAll() {
		int offet = 0;
		int  limit = 100;
		List<Seckill> s = dao.queryAll(offet, limit);
		System.out.println(s);
	}
	@Test
	public void resuceNum() {
		long id = 1;
		int s = dao.reduceNumber(id,new Date());
		System.out.println(s);
	}
	
	@Test
	public void insertSuccessed() {
		long id = 1;
		int s =sdao.insertSuccessKilled(id,123456);
		System.out.println(s);
	}
	
	@Test
	public void queryByIdWithSeckill() {
		long id = 1;
		SuccessKilled s =sdao.queryByIdWithSeckill(id,123456);
		System.out.println(s);
	}
	
	@Test
	public void testRedis() {
		RedisDao redisDao = new RedisDao("120.79.10.49",6379,"123456");
		Seckill seckill = redisDao.getSeckill(1);
		if(seckill==null) {
			seckill = dao.queryById(1);
			if(seckill!=null) {
				String res = redisDao.putSeckill(seckill);
				System.out.println("res:"+res);
				seckill = redisDao.getSeckill(1);
				System.out.println("seckill:"+seckill);
			}
		}
	}
}
