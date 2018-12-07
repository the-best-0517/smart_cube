package cn.buu.seckill.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import cn.buu.seckill.dao.SeckillDao;
import cn.buu.seckill.dao.SuccessKilledDao;
import cn.buu.seckill.dao.cache.RedisDao;
import cn.buu.seckill.dto.Exposer;
import cn.buu.seckill.dto.SeckillExcution;
import cn.buu.seckill.entity.Seckill;
import cn.buu.seckill.entity.SuccessKilled;
import cn.buu.seckill.exception.RepeatKillException;
import cn.buu.seckill.exception.SeckillCloseException;
import cn.buu.seckill.exception.SeckillException;
import cn.buu.seckill.service.SeckillService;
@Service
public class SeckillServiceImpl implements SeckillService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private SeckillDao seckillDao;
	@Resource
	private SuccessKilledDao successKilledDao;
	@Resource
	private RedisDao redisDao;
	//md5盐值
	private final String slat = "lsc";
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 100);
	}

	@Override
	public Seckill getSeckillById(long SeckillId) {
		return seckillDao.queryById(SeckillId);
	}

	@Override 
	public Exposer exportSeckillUrl(long SeckillId) {
		/**优化点：缓存优化*/
		Seckill seckill = redisDao.getSeckill(SeckillId);	//1.缓存中查找
		if(seckill==null) {									//2.缓存中没有
			 seckill  = seckillDao.queryById(SeckillId);	//3.数据库中查找
			 if(seckill==null) {
					return new Exposer(false,SeckillId);
			 }else {
				 redisDao.putSeckill(seckill);					//4.放入缓存
			 }
			
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if(nowTime.before(startTime)||nowTime.after(endTime)) {
			return new Exposer(false,SeckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		String md5 = getMd5(SeckillId);
		return new Exposer(true,md5,SeckillId);
	}

	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的优点：
	 * 1.一致约定，明确编程风格
	 * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，或者剥离出来
	 * 3.不是所有方法都需要事务（一条修改操作，只读操作）
	 */
	public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		try {
			if(md5==null||!md5.equals(getMd5(seckillId))) {
				throw new SeckillException("秒杀数据错误！");
			}
			//执行秒杀逻辑 ：减库存，记录秒杀行为】
			Date nowTime = new Date();
			int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
			if(updateCount<=0) {
				throw new SeckillCloseException("秒杀结束了");
			}else {
				//记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount<=0) {
					//重复秒杀
					throw new RepeatKillException("重复秒杀");
				}else {
					//秒杀成功
					SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExcution(seckillId, 1,"秒杀成功", sk);
				}
			}
		}catch(SeckillException ee) {
			throw ee;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			//所有编译异常转换为运行异常
			throw new SeckillException("秒杀错误："+e.getMessage());
		}
	}
	private String getMd5(long seckillId) {
		String base =  seckillId+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
