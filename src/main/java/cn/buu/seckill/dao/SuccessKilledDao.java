package cn.buu.seckill.dao;

import org.apache.ibatis.annotations.Param;

import cn.buu.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * 根据Id查询成功明细，，并携带秒杀产品对象
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
