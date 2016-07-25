package com.chanchal.multithreading.executerservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * This class shows example of basic ThreadPool Concept. Thread Pools are useful
 * when you need to limit the number of threads running in your application at
 * the same time. There is a performance overhead associated with starting a new
 * thread, and each thread is also allocated some memory for its stack etc.
 * 
 * 
 * 
 * The best example where this example can be used is when you design server
 * which serves multiple client request at time. All the task from client get
 * pushed in BlockingQueue from where idle thread from ThreadPool picks the task
 * and then execute it.
 */
class ThreadPoolPojo implements Runnable {
	private int serialNumber;

	public ThreadPoolPojo(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public void run() {
		System.out.println("Starting Thread With serialnumber : " + serialNumber);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("Completed Thread With SerialNumber : " + serialNumber);
	}

}

public class ThreadPoolExample {
	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(2);

		for (int i = 0; i < 12; i++) {
			service.submit(new ThreadPoolPojo(i));
		}

		service.shutdown();
		try {
			service.awaitTermination(10, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
