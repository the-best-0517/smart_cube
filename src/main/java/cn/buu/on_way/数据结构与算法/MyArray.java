package cn.buu.on_way.���ݽṹ���㷨;

import java.util.Arrays;

/**
 * ����������
 * @author ABC
 *
 */
public class MyArray {
		//���ڴ洢���ݵ�����
		private int[] elements;
		
		public MyArray() {
			
			this.elements = new int[0];
		}
		public MyArray(int z) {
			
			this.elements = new int[z];
		}
		//��ȡ����
		public int size() {
			return this.elements.length;
		}
		//����ĩβ���Ԫ��
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
		//�鿴����
		public void show() {
			System.out.println(Arrays.toString(elements));
		}
		//����Ԫ��	
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