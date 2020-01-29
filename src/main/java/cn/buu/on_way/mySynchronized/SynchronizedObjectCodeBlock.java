package cn.buu.on_way.mySynchronized;

/**
 * ������ʾ��1  �������ʽ
 * @author ABC
 *
 */
public class SynchronizedObjectCodeBlock implements Runnable{
	static SynchronizedObjectCodeBlock socb = new SynchronizedObjectCodeBlock();
	Object lock1 = new Object();
	@Override
	public void run() {
		synchronized(lock1){					//��֤����鴮��ִ��
			System.out.println("���Ƕ������Ĵ������ʽ��"+Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("ִ�����"+Thread.currentThread().getName());
		}
	}
	public static void main(String[] args) {
		Thread t1 = new Thread(socb);
		Thread t2 = new Thread(socb);
		t1.start();
		t2.start();
		//t1.join();
		//t2.join();
		while(t1.isAlive()||t2.isAlive()) {
			
		}
		System.out.println("ok");
	}
}	
