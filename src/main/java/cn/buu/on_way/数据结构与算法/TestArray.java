package cn.buu.on_way.���ݽṹ���㷨;

import java.util.List;
import java.util.Stack;

/**
 * ///�㷨�ϼ�
 * @author ABC
 *
 */
public class TestArray {
	public static void main(String[] args) {
		//
		int[] arr = new int[] {1,2,3,6,9,19,28,37,49,55};
		int index = lineSearch(arr,777);
		System.out.println(index);
		System.out.println("=================================================");
		index = binarySearch(arr,19);
		System.out.println(index);
		System.out.println("=================================================");
		Stack<Integer> s = new Stack<>();
		s.push(1);
		s.push(2);
		s.push(3);
		System.out.println(s.pop());
		System.out.println(s.pop());
	}
	/**
	 * ���ַ�����
	 * @param arr
	 * @param i
	 * @return
	 */
	private static int binarySearch(int[] arr, int i) {
        int begin = 0;
        int end = arr.length-1;
        int middle =(begin+end)/2;
        while(true) {
        	if(middle==0||middle==arr.length-1) {
        		return -1;
        	}
        	if(arr[middle]==i) {
        		return middle;
        	}else {
        		if(arr[middle]>i) {
        			end = middle-1;
        		}else {
        			begin = middle+1;
        		}
        	}
        	middle =(begin+end)/2;
        }
	}
	/**
	 * ���Բ���
	 * @param arr
	 * @param i
	 * @return
	 */
	private static int lineSearch(int[] arr, int a) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]==a) {
				return i;
			}
		}
		return -1;
	}
}	
