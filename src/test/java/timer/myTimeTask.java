package timer;

import java.util.TimerTask;

public class myTimeTask extends TimerTask{
	private String name;
	public myTimeTask(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		System.out.println("ĞÕÃûÎª£º"+name);
	}

}
