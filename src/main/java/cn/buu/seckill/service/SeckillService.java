package cn.buu.seckill.service;

import java.util.List;

import cn.buu.seckill.dto.Exposer;
import cn.buu.seckill.dto.SeckillExcution;
import cn.buu.seckill.entity.Seckill;
import cn.buu.seckill.exception.RepeatKillException;
import cn.buu.seckill.exception.SeckillCloseException;
import cn.buu.seckill.exception.SeckillException;

/**
 * վ��ʹ���ߵĽǶ���ƽӿ�
 * �������棺
 * 		1��������������
 * 		2������  ����ֱ��
 * 		3���������ͣ�return/�쳣��
 * @author ABC
 *
 */
public interface SeckillService {
	List<Seckill> getSeckillList();
	Seckill getSeckillById(long SeckillId);
	/**
	 * ��ɱ��ʼ�������ɱ�ӿڵ�ַ
	 * �������ϵͳʱ�����ɱʱ��
	 */
	Exposer exportSeckillUrl(long SeckillId);
	
	/**
	 * ִ����ɱ����
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExcution excuteSeckill(long seckillId,long userPhone,String md5)
			throws SeckillException,RepeatKillException,SeckillCloseException;
}
