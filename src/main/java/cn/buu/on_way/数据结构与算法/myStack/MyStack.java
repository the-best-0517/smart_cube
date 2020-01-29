package cn.buu.on_way.���ݽṹ���㷨.myStack;
/**
 * ջ   ������
 * java�ṩ��stack����
 * ������ʵ�ַ�ʽ
 * @author ABC
 * @param <T>
 */
public class MyStack<T> {
	 private Object[] elements;       //����Ԫ��
	 
	 public MyStack() {
		super();
		elements = new Object[0];
	}
	//��ջ   ����Ԫ��
	 public void push(T obj) {
		 Object[] newArr = new Object[elements.length+1];
		 for(int i=0;i<elements.length;i++) {
			 newArr[i] = elements[i];
		 }
		 newArr[newArr.length] = obj;
		 elements = newArr;
	 }
	 //��ջ  ����Ԫ��
	 @SuppressWarnings("unchecked")
	public T pop() {
		if(elements.length==0) {
			throw new RuntimeException("��ջ");
		}
		T pop = (T) elements[elements.length-1];
		//ɾ������Ԫ��
		Object[] newArr = new Object[elements.length-1];
		 for(int i=0;i<elements.length;i++) {
			 newArr[i] = elements[i];
		 }
		 elements = newArr;
		return pop;
	 }
	 //�鿴ջ��Ԫ��
	 @SuppressWarnings("unchecked")
	public T peek() {
		 if(elements.length==0) {
				throw new RuntimeException("��ջ");
		 }
		 return (T)elements[elements.length-1];
	 }
}
