package cn.buu.on_way.���ݽṹ���㷨.����;

public class DoubleNode {
	private DoubleNode pre = this;
	private DoubleNode next = this;
	
	int data;
	public DoubleNode(int data) {
		this.data = data;
	}
	//���ӽڵ�
	public void insert(DoubleNode dn) {
		DoubleNode nNext = this.next;
		this.next = dn;
		dn.pre = this;
		
		dn.next = nNext;
		nNext.pre = dn;
	}
}
