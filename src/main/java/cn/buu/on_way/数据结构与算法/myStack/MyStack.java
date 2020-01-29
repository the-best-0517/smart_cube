package cn.buu.on_way.数据结构与算法.myStack;
/**
 * 栈   先入后出
 * java提供了stack对象
 * 这里是实现方式
 * @author ABC
 * @param <T>
 */
public class MyStack<T> {
	 private Object[] elements;       //声明元素
	 
	 public MyStack() {
		super();
		elements = new Object[0];
	}
	//入栈   加入元素
	 public void push(T obj) {
		 Object[] newArr = new Object[elements.length+1];
		 for(int i=0;i<elements.length;i++) {
			 newArr[i] = elements[i];
		 }
		 newArr[newArr.length] = obj;
		 elements = newArr;
	 }
	 //出栈  弹出元素
	 @SuppressWarnings("unchecked")
	public T pop() {
		if(elements.length==0) {
			throw new RuntimeException("空栈");
		}
		T pop = (T) elements[elements.length-1];
		//删除弹出元素
		Object[] newArr = new Object[elements.length-1];
		 for(int i=0;i<elements.length;i++) {
			 newArr[i] = elements[i];
		 }
		 elements = newArr;
		return pop;
	 }
	 //查看栈顶元素
	 @SuppressWarnings("unchecked")
	public T peek() {
		 if(elements.length==0) {
				throw new RuntimeException("空栈");
		 }
		 return (T)elements[elements.length-1];
	 }
}
