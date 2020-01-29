package cn.buu.on_way.数据结构与算法.链表;

public class Node<E> {
	//链表数据
	private E data;
	//下一个节点
	private Node<E> next;
	
	
	public Node(E data) {
		super();
		this.data = data;
	}
	//追加节点
	public Node<E> append(Node<E> node) {
		Node<E> currentNode = this;
		//遍历寻找到最后一个节点
		while(true) {
			Node<E> nextNode = currentNode.next;
			if(nextNode==null) {
				break;
			}
			currentNode = nextNode;
		}
		currentNode.next = node;
		return this;
	}
	//获取下一个节点
	public Node<E> next(){
		return this.next;
	}
	/**根据坐标获取节点  
	*	index  :   第几个节点  从1开始数
	*/
	public Node<E> next(int index){
		Node<E> currentNode = this;
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
		Node<E> currentNode = this;
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
	public void insert(Node<E> node,int index) {
		Node<E> n = this.next(index);
		node.next = n.next;
		n.next = node;
	}
	public void insert(Node<E> node) {
		node.next = this.next.next;
		this.next = node;
	}
}
