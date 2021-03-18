package test.Semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
	private static final int THREAD_COUNT = 30;
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
	private static Semaphore s = new Semaphore(10);

	   public static void main(String[] args)
	   {
		int ccc=Runtime.getRuntime().availableProcessors();
		System.out.println("cpu个数："+ccc);
		   
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						s.acquire();
						System.out.println("save data");
						Thread.sleep(3000);
						s.release();
					
					} catch (InterruptedException e) {
					}
				}
			});
		}
		threadPool.shutdown();
	}
}
