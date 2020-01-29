package cn.buu.on_way.���ݽṹ���㷨.����;

public class Node<E> {
	//��������
	private E data;
	//��һ���ڵ�
	private Node<E> next;
	
	
	public Node(E data) {
		super();
		this.data = data;
	}
	//׷�ӽڵ�
	public Node<E> append(Node<E> node) {
		Node<E> currentNode = this;
		//����Ѱ�ҵ����һ���ڵ�
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
	//��ȡ��һ���ڵ�
	public Node<E> next(){
		return this.next;
	}
	/**���������ȡ�ڵ�  
	*	index  :   �ڼ����ڵ�  ��1��ʼ��
	*/
	public Node<E> next(int index){
		Node<E> currentNode = this;
		for(int i=0;i<index;i++) {
			currentNode = currentNode.next;
		}
		return currentNode;
	}
	//��ȡ��������
	public E getData() {
		return this.data;
	}
	//ɾ����һ���ڵ�
	public void removeNext() {
		this.next = next.next;
	}
	//��ӡ���нڵ�
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
	 * ����ڵ�
	 * @param node	 �ڵ�
	 * @param index	 �ڼ����ڵ�  ��1��ʼ��
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
