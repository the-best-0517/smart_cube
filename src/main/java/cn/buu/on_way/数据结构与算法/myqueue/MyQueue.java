package cn.buu.on_way.���ݽṹ���㷨.myqueue;
/**
 * ����    �����ȳ�
 * Java ����  Queueʵ��
 * �������Լ���������ĵײ�ʵ��
 * @author ABC
 * @param <E>
 */
public class MyQueue<E> {
	private Object[] elements;
	public MyQueue() {
		super();
		elements = new Object[0];
	}
	//�������
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
	//����  ����   (�����ȳ�ԭ��)
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
	//�����Ƿ�Ϊ��
	public boolean isEmpty() {
		if(elements.length==0) {
			return true;
		}
		return false;
	}
}
