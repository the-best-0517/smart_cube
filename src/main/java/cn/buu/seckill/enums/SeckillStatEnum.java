package cn.buu.seckill.enums;

/**
 * ʹ��ö�ٱ������������ֶ�
 * @author ABC
 *
 */
public class SeckillStatEnum {
	private int state;
	
	private String stateInfo;

	public SeckillStatEnum(int state, String stateInfo) {
		super();
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	public static SeckillStatEnum stateOf(int index) {
	
		return null;
		
	}
}
