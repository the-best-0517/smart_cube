package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.buu.seckill.dto.Exposer;
import cn.buu.seckill.dto.SeckillExcution;
import cn.buu.seckill.entity.Seckill;
import cn.buu.seckill.exception.SeckillException;
import cn.buu.seckill.service.SeckillService;

public class SeckillServiceTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private SeckillService seckillService;
	@Before
   	public void init() {
		ClassPathXmlApplicationContext	ctx=new ClassPathXmlApplicationContext(
				"spring-mvc.xml",
				"spring-pool.xml",
				"spring-mybatis.xml",
				"mybatis-config.xml");
      	   // ApplicationContext ac  = new ClassPathXmlApplicationContext(config);
      	   // dao = ctx.getBean("seckillDao", SeckillDao.class);
		//seckillService = ctx.getBean("SeckillService", SeckillService.class);
   	}	
	@Test
	public void getSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		System.out.println(list);
	}
	@Test
	public void getSeckillById() {
		long SeckillId = 1;
		Seckill s = seckillService.getSeckillById(SeckillId);
		System.out.println(s);
	}
	
	@Test
	public void exportSeckillUrl() {
		long SeckillId = 1;
		Exposer e = seckillService.exportSeckillUrl(SeckillId);
		System.out.println(e);
	}
	
	@Test
	public void excuteSeckill() {
		long seckillId = 1;
		long userPhone = 123456;
		String md5 = "lsc";
		SeckillExcution se = seckillService.excuteSeckill(seckillId, userPhone, md5);
		System.out.println(se);
	}
	
}
