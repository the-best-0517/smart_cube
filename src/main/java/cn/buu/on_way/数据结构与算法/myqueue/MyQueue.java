package cn.buu.on_way.数据结构与算法.myqueue;
/**
 * 队列    先入先出
 * Java 中用  Queue实现
 * 这里是自己利用数组的底层实现
 * @author ABC
 * @param <E>
 */
public class MyQueue<E> {
	private Object[] elements;
	public MyQueue() {
		super();
		elements = new Object[0];
	}
	//加入队列
	@SuppressWarnings("unchecked")
	public E add(E e) {
		Object[] newArray = new Object[elements.length+1];
		for(int i=0;i<elements.length;i++) {
			newArray[i] = elements[i];
		}
		newArray[elements.length] = e;
		elements = newArray;
		return (E) elements.toString();
	}
	//弹出  队列   (先入先出原则)
	@SuppressWarnings("unchecked")
	public E poll() {
		Object[] newArray = new Object[elements.length-1];
		for(int i=0;i<newArray.length;i++) {
			newArray[i] = elements[i+1];
		}
		E poll = (E) elements[0];
		elements = newArray;
		return poll;
	}
	//队列是否为空
	public boolean isEmpty() {
		if(elements.length==0) {
			return true;
		}
		return false;
	}
}
