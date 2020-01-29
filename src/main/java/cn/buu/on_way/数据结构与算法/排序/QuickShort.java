package cn.buu.on_way.数据结构与算法.排序;

import java.util.Arrays;
/**
 * 快速排序
 * @author ABC
 *
 */
public class QuickShort {
	
	public static void main(String[] args) {
		int[] arr = {50,5,4,3,9,8,7,10,100,52,3};
		quickShort(arr,0,arr.length-1);
		System.out.println(Arrays.toString(arr));
	}
	public static void quickShort(int[] arr,int start,int end) {
		if(start<end) {
			int low = start;
			int height = end;
			int stand = arr[start];
			while(low<height) {
				//后和基本数比较
				//比基本数大则继续
				while(arr[height]>=stand&&low<height) {
					height--;
				}
				//比基本数小 互换位置
				arr[low] = arr[height];
				//前和基本数比较
				while(arr[low]<=stand&&low<height) {
					low++;
				}
				arr[height] = arr[low];
				
			}
			arr[low] = stand;
			//将标准数前半部分排序
			quickShort(arr,0,low);
			//将标准数前半部分排序
			quickShort(arr,low+1,end);
		}
	}
}

