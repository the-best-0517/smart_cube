package cn.buu.on_way.数据结构与算法.链表;

public class LoopNode<E> {
	//链表数据
	private E data;
	//下一个节点
	private LoopNode<E> next = this;
	
	
	public LoopNode(E data) {
		super();
		this.data = data;
	}
	//追加节点
	public LoopNode<E> append(LoopNode<E> node) {
		LoopNode<E> currentNode = this;
		//遍历寻找到最后一个节点
		while(true) {
			LoopNode<E> nextNode = currentNode.next;
			if(nextNode==null) {
				break;
			}
			currentNode = nextNode;
		}
		currentNode.next = node;
		return this;
	}
	//获取下一个节点
	public LoopNode<E> next(){
		return this.next;
	}
	/**根据坐标获取节点  
	*	index  :   第几个节点  从1开始数
	*/
	public LoopNode<E> next(int index){
		LoopNode<E> currentNode = this;
		for(int i=0;i<index;i++) {
			currentNode = currentNode.next;
		}
		return currentNode;
	}
	//获取链表数据
	public E getData() {
		return this.data;
	}
	//删除下一个节点
	public void removeNext() {
		this.next = next.next;
	}
	//打印所有节点
	public void showAll() {
		LoopNode<E> currentNode = this;
		while(true) {
			System.out.print(currentNode.data+",");
			currentNode = currentNode.next;
			if(currentNode==null) {
				break;
			}
		}
	}
	/**
	 * 插入节点
	 * @param node	 节点
	 * @param index	 第几个节点  从1开始数
	 */
	public void insert(LoopNode<E> node,int index) {
		LoopNode<E> n = this.next(index);
		node.next = n.next;
		n.next = node;
	}
	public void insert(LoopNode<E> node) {
		this.next = node;
		node.next = this.next;
	}
}
