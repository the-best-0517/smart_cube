package cn.buu.on_way.���ݽṹ���㷨.�ݹ�;

/**
 * �ݹ�
 * @author ABC
 *
 */
public class Recursion {
		private static int[] arr ;
		public static void main(String[] args) {
			print(10);
			//쳲���������    1 1 2 3 5 8 13
			int fe = printFebonacci(3);
			System.out.println("fe:"+fe);
			System.out.println("=================================");
			//��ŵ������
			hannoi(5,'A','B','C');
		}
		//��ŵ������
		/**
		 * 
		 * @param i		��������
		 * @param from	��ʼλ��
		 * @param in	�м�λ��
		 * @param to	Ŀ��λ��
		 * ����������Ϊ  ���ϱ����������Ƶ��м䣬���һ���Ƶ�Ŀ��λ��
		 */
		private static void hannoi(int i, char from, char in, char to) {
			if(i==1) {
				System.out.println("��1��"+from+"�ƶ���"+to);
			}else {
				hannoi(i-1,from,to,in);
				System.out.println("��"+i+"��"+from+"�ƶ���"+to);
				hannoi(i-1,in,from,to);
			}
		}
		
		//��ӡ ָ���� 쳲���������
		private static int printFebonacci(int i) {
			if(i==1||i==2) {
				return 1;
			}else {
				return printFebonacci(i-1)+printFebonacci(i-2);
			}
		}
		private static void print(int i) {
			if(i>0) {
				System.out.println(i);
				print(i-1);
			}
		}
		
		
}
