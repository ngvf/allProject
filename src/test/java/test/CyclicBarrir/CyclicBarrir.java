package test.CyclicBarrir;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrir  {
	static CyclicBarrier c = new CyclicBarrier(2);
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					c.await();
				} catch (Exception e) {
				}
				System.out.println(1);
			}
		}).start();
		try {
			c.await();
		} catch (Exception e) {
		}
		System.out.println(2);
	}
	static class A implements Runnable {
		@Override
		public void run() {
			System.out.println(3);
		}
	}

}
