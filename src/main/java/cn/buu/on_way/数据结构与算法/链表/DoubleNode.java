package cn.buu.on_way.数据结构与算法.链表;

public class DoubleNode {
	private DoubleNode pre = this;
	private DoubleNode next = this;
	
	int data;
	public DoubleNode(int data) {
		this.data = data;
	}
	//增加节点
	public void insert(DoubleNode dn) {
		DoubleNode nNext = this.next;
		this.next = dn;
		dn.pre = this;
		
		dn.next = nNext;
		nNext.pre = dn;
	}
}
