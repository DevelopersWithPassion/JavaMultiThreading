package com.chanchal.multithreading.synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author chanchal.singh
 *
 *         This class basically shows example of how Synchronization block is
 *         preferred instead of synchronized method. synchronized method aquires
 *         lock over whole object which is holding that message it restrict the
 *         other thread to use other synchronized method if there is any.
 * 
 *         in this example if you remove synchronized block and make
 *         synchronized method and the observe execution time you will get it
 *         Around 4 millisecond.
 * 
 *         Now run this program and see the difference you will get time
 *         difference of 2 millisecond. This difference comes because while
 *         thread 1 is executing doWork1() method at same time thread 2 is
 *         executing doWork2() method. remember both method is not sharing
 *         common resources.
 */

public class SynchronizeBlockMultiLockExample {
	private Random rand = new Random();
	private Object lock = new Object();
	private Object lock1 = new Object();

	private List<Integer> numberList1 = new ArrayList<>();
	private List<Integer> numberList2 = new ArrayList<>();

	public void doWork1() {
		synchronized (lock) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numberList1.add(rand.nextInt(100));
		}

	}

	public void doWork2() {
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numberList2.add(rand.nextInt(100));
		}

	}

	public void process() {
		for (int i = 0; i < 1000; i++) {
			doWork1();
			doWork2();
		}
	}

	public void main() {
		System.out.println("String Thread to check total time with synchronized method");

		long start = System.currentTimeMillis();
		Thread test1 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}
		});
		Thread test2 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}
		});
		test1.start();
		test2.start();
		try {
			test1.join();
			test2.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("Total time taken " + (end - start));
		System.out.println("List1: " + numberList1.size() + " List2 : " + numberList2.size());

	}

	public static void main(String[] args) {
		SynchronizeBlockMultiLockExample test = new SynchronizeBlockMultiLockExample();
		test.main();
	}
}
