package com.imooc.ms.mytool.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class NewCachedThreadPool {

	public static void method() throws Exception { 
	    ExecutorService executor = Executors.newCachedThreadPool(); 
	 
	    for (int i = 0; i < 5; i++) { 
	 
	        final int index = i; 
	 
	       
	 
	        executor.execute(new Runnable() { 
	            @Override 
	            public void run() { 
	            	 try {
						Thread.sleep(9000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
	                System.out.println(Thread.currentThread().getName() + "  " + index); 
	            } 
	        }); 
	    } 
	} 
	
	
	public static void method_01() throws InterruptedException { 
		 
	    ExecutorService executor = Executors.newFixedThreadPool(3); 
	 
	    for (int i = 0; i < 10; i++) { 
	 
	        Thread.sleep(1000); 
	        final int index = i; 
	 
	        executor.execute(() -> { 
	            try { 
	                Thread.sleep(2 * 1000); 
	            } catch (InterruptedException e) { 
	                e.printStackTrace(); 
	            } 
	            System.out.println(Thread.currentThread().getName() + "  " + index); 
	        }); 
	    } 
	    executor.shutdown(); 
	} 
	
	
	
	public static void main(String[] args) throws Exception {
		method_01();
	}
	
	
}
