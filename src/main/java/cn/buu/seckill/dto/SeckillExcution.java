package cn.buu.seckill.dto;

import cn.buu.seckill.entity.SuccessKilled;

/**
 * ��װ��ɱִ�к�Ľ��
 * @author ABC
 *
 */
public class SeckillExcution {
	private long seckilld;
	//��ɱִ�н��״̬
	private int state;
	//״̬��ʾ
	private String stateInfo;
	//��ɱ�ɹ�����
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
