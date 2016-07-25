package com.chanhcal.multithreading.synchronization;

/*This class explains the concurrency problem when two or more thread access the shared resources at same time.
 * 
 * here you can see count variable access by two thread inside doSomething() method. 
 * The value of count at end should be 20000 but 
 * if you run program 3-4 times Continuously you will see value will not match as expected. Because both thread are 
 * sharing count variable and while incrementing they may ending up reading incorrect value and then increment it .
 * 
 * 
 * Now to Solve this problem we have to use Synchronization. Please see SynchronizationSolutionExample class for solution
 * */
public class SynchronizationProblemExample {
	private int count = 0;

	public static void main(String[] args) {
		SynchronizationProblemExample example=new SynchronizationProblemExample();
		example.doSomething();
	}

	public void doSomething() {
		Thread newThread = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 10000; i++) {
					count++;
				}
			}
		});

		Thread newThread1 = new Thread(new Runnable() {

			public void run() {
				for (int i = 0; i < 10000; i++) {
					count++;
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
		System.out.println("Count is" + count);
	}
}
