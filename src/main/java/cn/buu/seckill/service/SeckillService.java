package cn.buu.seckill.service;

import java.util.List;

import cn.buu.seckill.dto.Exposer;
import cn.buu.seckill.dto.SeckillExcution;
import cn.buu.seckill.entity.Seckill;
import cn.buu.seckill.exception.RepeatKillException;
import cn.buu.seckill.exception.SeckillCloseException;
import cn.buu.seckill.exception.SeckillException;

/**
 * 站在使用者的角度设计接口
 * 三个方面：
 * 		1）方法定义粒度
 * 		2）参数  简练直接
 * 		3）返回类型（return/异常）
 * @author ABC
 *
 */
public interface SeckillService {
	List<Seckill> getSeckillList();
	Seckill getSeckillById(long SeckillId);
	/**
	 * 秒杀开始：输出秒杀接口地址
	 * 否则输出系统时间和秒杀时间
	 */
	Exposer exportSeckillUrl(long SeckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExcution excuteSeckill(long seckillId,long userPhone,String md5)
			throws SeckillException,RepeatKillException,SeckillCloseException;
}
