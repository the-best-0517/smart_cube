package cn.buu.on_way.mySynchronized;

/**
 * 对象锁示例2  方法锁形式
 * @author ABC
 *
 */
public class SynchronizedObjectMethodBlock2 implements Runnable{
	static SynchronizedObjectMethodBlock2 socb = new SynchronizedObjectMethodBlock2();
	@Override
	public void run() {
		method();
	}
	public synchronized void method() {
		System.out.println("我是对象锁的方法修饰符形式，我叫"+Thread.currentThread().getName());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
