package cn.buu.on_way.数据结构与算法.test;

import java.util.Arrays;

public class qs {

	public static void main(String[] args) {
		int[] arr = {2,5,4,3,9,8,7,6,0,21,36};
		qs(arr,0,arr.length-1);
		System.out.println(Arrays.toString(arr));
	}

	private static void qs(int[] arr, int start, int end) {
		if(start<end) {
			int low = start;
			int height = end;
			int stand = arr[start];
			while(low<height) {
				while(low<height&&arr[height]>=stand) {
					height--;
				}
				arr[low] = arr[height];
				while(low<height&&arr[low]<=stand) {
					low++;
				}
				arr[height] = arr[low];
			}
			arr[low] = stand;
			qs(arr,0,low);
			qs(arr,low+1,end);
		}

	}
	
}
