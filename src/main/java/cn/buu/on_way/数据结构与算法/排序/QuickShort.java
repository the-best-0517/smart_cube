package cn.buu.on_way.���ݽṹ���㷨.����;

import java.util.Arrays;
/**
 * ��������
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
				//��ͻ������Ƚ�
				//�Ȼ������������
				while(arr[height]>=stand&&low<height) {
					height--;
				}
				//�Ȼ�����С ����λ��
				arr[low] = arr[height];
				//ǰ�ͻ������Ƚ�
				while(arr[low]<=stand&&low<height) {
					low++;
				}
				arr[height] = arr[low];
				
			}
			arr[low] = stand;
			//����׼��ǰ�벿������
			quickShort(arr,0,low);
			//����׼��ǰ�벿������
			quickShort(arr,low+1,end);
		}
	}
}

