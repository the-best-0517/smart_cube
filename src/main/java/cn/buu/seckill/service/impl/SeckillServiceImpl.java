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
	//md5��ֵ
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
		/**�Ż��㣺�����Ż�*/
		Seckill seckill = redisDao.getSeckill(SeckillId);	//1.�����в���
		if(seckill==null) {									//2.������û��
			 seckill  = seckillDao.queryById(SeckillId);	//3.���ݿ��в���
			 if(seckill==null) {
					return new Exposer(false,SeckillId);
			 }else {
				 redisDao.putSeckill(seckill);					//4.���뻺��
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
	 * ʹ��ע��������񷽷����ŵ㣺
	 * 1.һ��Լ������ȷ��̷��
	 * 2.��֤���񷽷���ִ��ʱ�価���̣ܶ���Ҫ��������������������߰������
	 * 3.�������з�������Ҫ����һ���޸Ĳ�����ֻ��������
	 */
	public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillCloseException {
		try {
			if(md5==null||!md5.equals(getMd5(seckillId))) {
				throw new SeckillException("��ɱ���ݴ���");
			}
			//ִ����ɱ�߼� ������棬��¼��ɱ��Ϊ��
			Date nowTime = new Date();
			int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
			if(updateCount<=0) {
				throw new SeckillCloseException("��ɱ������");
			}else {
				//��¼������Ϊ
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount<=0) {
					//�ظ���ɱ
					throw new RepeatKillException("�ظ���ɱ");
				}else {
					//��ɱ�ɹ�
					SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExcution(seckillId, 1,"��ɱ�ɹ�", sk);
				}
			}
		}catch(SeckillException ee) {
			throw ee;
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			//���б����쳣ת��Ϊ�����쳣
			throw new SeckillException("��ɱ����"+e.getMessage());
		}
	}
	private String getMd5(long seckillId) {
		String base =  seckillId+"/"+slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
