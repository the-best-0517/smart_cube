package cn.buu.seckill.dao;

import org.apache.ibatis.annotations.Param;

import cn.buu.seckill.entity.SuccessKilled;

public interface SuccessKilledDao {
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * ����Id��ѯ�ɹ���ϸ������Я����ɱ��Ʒ����
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
}
