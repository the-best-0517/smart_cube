package cn.buu.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.buu.seckill.entity.Seckill;
@Repository
public interface SeckillDao {
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date killTime);
	
	Seckill queryById(long seckillId);
	
	List<Seckill> queryAll(@Param("offet")int offet,@Param("limit")int limit);
}
