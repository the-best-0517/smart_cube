package cn.buu.on_way.���ݽṹ���㷨.myqueue;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueueMain {
	  
	public static void main(String[] args) {
//		 Queue<String> queue = new LinkedList<String>();
//		 queue.offer("");  
		
		MyQueue<Integer> queue = new MyQueue<>();
		queue.add(1);
		queue.add(2);
		System.out.println(queue.poll());
		System.out.println(queue.isEmpty());
		System.out.println(queue.poll());
		System.out.println(queue.isEmpty());
	}

}
