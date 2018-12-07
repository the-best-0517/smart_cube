package timer;

import java.util.Timer;

public class MyTimer {
	public static void main(String[] args) throws InterruptedException {
		//1.
		Timer timer = new Timer();
		//2.
		myTimeTask  timerTask = new myTimeTask("lsc");
		//3.
		timer.schedule(timerTask, 2000,3000);
		Thread.sleep(6000);
		System.out.println("timer取消数量："+timer.purge());
		timerTask.cancel();
		System.out.println("timertASK cancel");
		
		//timer.cancel();
		//System.out.println("timertASK cancel");
		System.out.println("timer取消数量："+timer.purge());
		
	}
}
