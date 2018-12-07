package cn.buu.seckill.dto;

import cn.buu.seckill.entity.SuccessKilled;

/**
 * 封装秒杀执行后的结果
 * @author ABC
 *
 */
public class SeckillExcution {
	private long seckilld;
	//秒杀执行结果状态
	private int state;
	//状态表示
	private String stateInfo;
	//秒杀成功对象
	private SuccessKilled successkilled;
	public SeckillExcution(long seckilld, int state, String stateInfo, SuccessKilled successkilled) {
		super();
		this.seckilld = seckilld;
		this.state = state;
		this.stateInfo = stateInfo;
		this.successkilled = successkilled;
	}
	public long getSeckilld() {
		return seckilld;
	}
	public void setSeckilld(long seckilld) {
		this.seckilld = seckilld;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public SuccessKilled getSuccesskilled() {
		return successkilled;
	}
	public void setSuccesskilled(SuccessKilled successkilled) {
		this.successkilled = successkilled;
	}
	@Override
	public String toString() {
		return "SeckillExcution [seckilld=" + seckilld + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successkilled=" + successkilled + "]";
	}
	
	
}
