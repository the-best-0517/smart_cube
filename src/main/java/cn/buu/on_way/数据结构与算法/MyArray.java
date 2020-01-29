package cn.buu.on_way.数据结构与算法;

import java.util.Arrays;

/**
 * 操作数组类
 * @author ABC
 *
 */
public class MyArray {
		//用于存储数据的数组
		private int[] elements;
		
		public MyArray() {
			
			this.elements = new int[0];
		}
		public MyArray(int z) {
			
			this.elements = new int[z];
		}
		//获取长度
		public int size() {
			return this.elements.length;
		}
		//数组末尾添加元素
		public void add(int element) {
			int i=0;
			int[] newArr = new int[this.elements.length+1];
			for(int e:this.elements) {
				newArr[i] = e;
				i++;
			}
			newArr[i] = element;
			elements = newArr;
		}
		//查看数组
		public void show() {
			System.out.println(Arrays.toString(elements));
		}
		//插入元素	
		public void insert(int index,int element) {
			int[] newArr = new int[elements.length+1];
			for(int i=0;i<newArr.length;i++) {
				if(i<index) {
					newArr[i]=elements[i];
				}else {
					newArr[i+1] = elements[i];
				}
			}
			newArr[index] = element;
		}
}