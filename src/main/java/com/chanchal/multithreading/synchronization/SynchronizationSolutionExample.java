package com.chanchal.multithreading.synchronization;

/**
 * This class shows example how we can avoid concurrency problem on shared data.
 * 
 * now you can see some differences here from SynchronizationProblemExample
 * class example. now we have changed way count get incremented. we are
 * incrementing it using increamentCount() which is synchronized that means
 * whenever any thread start using this method no other thread can actually have
 * access to it.
 * 
 * When Method is synchronized Thread which start using it acquires lock over
 * it. this method is not performance effective
 * 
 * Now if you run program you will see expected value of count get printed
 */
public class SynchronizationSolutionExample {
	private int count = 0;

	private synchronized void increamentCount() {
		count++;
	}

	public static void main(String[] args) {
		SynchronizationSolutionExample example = new SynchronizationSolutionExample();
		example.doSomething();
	}

	public void doSomething() {
		Thread newThread = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 10000; i++) {
					increamentCount();
				}
			}
		});

		Thread newThread1 = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 10000; i++) {
					increamentCount();
				}
			}
		});

		newThread.start();
		newThread1.start();

		try {
			newThread.join();
			newThread1.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Count is " + count);
	}
}
