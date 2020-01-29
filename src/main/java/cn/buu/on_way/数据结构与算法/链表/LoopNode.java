package cn.buu.on_way.���ݽṹ���㷨.����;

public class LoopNode<E> {
	//��������
	private E data;
	//��һ���ڵ�
	private LoopNode<E> next = this;
	
	
	public LoopNode(E data) {
		super();
		this.data = data;
	}
	//׷�ӽڵ�
	public LoopNode<E> append(LoopNode<E> node) {
		LoopNode<E> currentNode = this;
		//����Ѱ�ҵ����һ���ڵ�
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
	//��ȡ��һ���ڵ�
	public LoopNode<E> next(){
		return this.next;
	}
	/**���������ȡ�ڵ�  
	*	index  :   �ڼ����ڵ�  ��1��ʼ��
	*/
	public LoopNode<E> next(int index){
		LoopNode<E> currentNode = this;
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
	 * ����ڵ�
	 * @param node	 �ڵ�
	 * @param index	 �ڼ����ڵ�  ��1��ʼ��
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
