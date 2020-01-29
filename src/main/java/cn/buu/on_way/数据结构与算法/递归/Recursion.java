package cn.buu.on_way.数据结构与算法.递归;

/**
 * 递归
 * @author ABC
 *
 */
public class Recursion {
		private static int[] arr ;
		public static void main(String[] args) {
			print(10);
			//斐波那契数列    1 1 2 3 5 8 13
			int fe = printFebonacci(3);
			System.out.println("fe:"+fe);
			System.out.println("=================================");
			//汉诺塔问题
			hannoi(5,'A','B','C');
		}
		//汉诺塔问题
		/**
		 * 
		 * @param i		几个盘子
		 * @param from	开始位置
		 * @param in	中间位置
		 * @param to	目标位置
		 * 将此问题拆分为  将上边所有盘子移到中间，最后一个移到目标位置
		 */
		private static void hannoi(int i, char from, char in, char to) {
			if(i==1) {
				System.out.println("将1从"+from+"移动到"+to);
			}else {
				hannoi(i-1,from,to,in);
				System.out.println("将"+i+"从"+from+"移动到"+to);
				hannoi(i-1,in,from,to);
			}
		}
		
		//打印 指定项 斐波那契数列
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
