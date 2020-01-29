package cn.buu.on_way.mySynchronized;

/**
 * 对象锁示例1  代码块形式
 * @author ABC
 *
 */
public class SynchronizedObjectCodeBlock implements Runnable{
	static SynchronizedObjectCodeBlock socb = new SynchronizedObjectCodeBlock();
	Object lock1 = new Object();
	@Override
	public void run() {
		synchronized(lock1){					//保证代码块串行执行
			System.out.println("我是对象锁的代码块形式："+Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("执行完毕"+Thread.currentThread().getName());
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
