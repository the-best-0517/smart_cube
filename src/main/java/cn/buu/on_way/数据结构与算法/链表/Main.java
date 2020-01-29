package cn.buu.on_way.数据结构与算法.链表;

public class Main {

	public static void main(String[] args) {
		Node<Integer> node1 = new Node<Integer>(1);
		Node<Integer> node2 = new Node<Integer>(2);
		Node<Integer> node3 = new Node<Integer>(3);
		Node<Integer> node4 = new Node<Integer>(4);
		node1.append(node2).append(node3).append(node4);
		//System.out.println(node1.next().next().getData());
/*		node1.showAll();
		node2.removeNext();
		System.out.println();
		node1.showAll();*/
		System.out.println("===========================================");
		System.out.println(node1.next(2).getData());
		System.out.println("===========================================");
		Node<Integer> node5 = new Node<Integer>(5);
		node1.insert(node5, 1);
		node1.showAll();
	}

}
