package cn.buu.seckill.entity;

import java.util.Date;

public class SuccessKilled {
	private long seckillId;
	private long phone;
	private short state;
	private Date createTime;
	 
	private Seckill seckill;

	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public short getState() {
		return state;
	}
	public void setState(short state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	public long getSeckillId() {
		return seckillId;
	}
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}
	public Seckill getSeckill() {
		return seckill;
	}
	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}
	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", phone=" + phone + ", state=" + state + ", createTime="
				+ createTime + ", seckill=" + seckill + "]";
	}

	
	
}
