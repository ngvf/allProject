package com.imooc.ms.mytool.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
 * Condition 是通过lock接口的实现类创建的
 * Condition 必须在获取锁和释放锁之间使用
 * 可以在lock实现类中获取多个Condition
 * Condition.await方法是当前线程释放锁，进入阻塞
 * Condition.signal方法是通知同一个condition的进入await的线程可以执行
 * 
 * 应用例子： 1.单个condition
 *         2.2个condition 适合生产者消费者模式
 *         3.3个condition 适合顺序通信
 * 
 */
public class ShareResource {
/*
 * 多线程之间按顺序执行，实现A->B->C三个线程启动，要求如下： A打印5次，B打印10次，C打印15次
 * 紧接着 A打印5次，B打印10次，C打印15次， . . . 循环执行10轮
 */
	// A:1 B:2 C:3
	private int num = 1;
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();
	private Condition conditionC = lock.newCondition();
 
	// 循环打印5次
	public void print5() {
		// 1、获取锁资源
		lock.lock();
		try {
			// 2、判断是否可以执行业务
			while (num != 1) {
				 // 阻塞等待
				conditionA.await();//是  则 让该线程 进入等待
			}
			// 模拟业务执行
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
			}
			// 3、通知其他线程，通过signal()方法唤醒线程
			num = 2;
			conditionB.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
 
	// 循环打印10次
	public void print10() {
		// 1、获取锁资源
		lock.lock();
		try {
			// 2、判断是否可以执行业务
			while (num != 2) {
				conditionB.await();//是  则 让该线程 进入等待
			}
			// 模拟业务执行
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
			}
			// 3、通知其他线程
			num = 3;
			conditionC.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
 
	// 循环打印15次
	public void print15() {
		// 1、获取锁资源
		lock.lock();
		try {
			// 2、判断是否可以执行业务
			while (num != 3) {
				conditionC.await();//是  则 让该线程 进入等待
			}
			// 模拟业务执行
			for (int i = 0; i < 15; i++) {
				System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
			}
			// 3、通知其他线程
			num = 1;
			conditionA.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	//---------------------------------------
	public static void main(String[] args) {
		ShareResource shareResource = new ShareResource();
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareResource.print5();
			}
		} , "A").start();
 
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareResource.print10();
			}
		} , "B").start();
 
		new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				shareResource.print15();
			}
		} , "C").start();
	}


	
}
