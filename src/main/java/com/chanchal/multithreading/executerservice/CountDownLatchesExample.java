package com.chanchal.multithreading.executerservice;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Use CountDownLatch when one of Thread like main thread, require to wait for
 * one or more thread to complete, before its start doing processing. Classical
 * example of using CountDownLatch in Java is any server side core Java
 * application which uses services architecture, where multiple services is
 * provided by multiple threads and application can not start processing until
 * all services have started successfully as shown in our CountDownLatch
 * example.
 * 
 * 
 * 
 * CountDownLatch in Java – Things to remember Few points about Java
 * CountDownLatch which is worth remembering:
 * 
 * 1) You can not reuse CountDownLatch once count is reaches to zero, this is
 * the main difference between CountDownLatch and CyclicBarrier.
 * 
 * 2) Main Thread wait on Latch by calling CountDownLatch.await() method while
 * other thread calls CountDownLatch.countDown() to inform that they have
 * completed.
 **/
public class CountDownLatchesExample {
	public static void main(String[] args) {
		CountDownLatch countLatch = new CountDownLatch(2);

		ExecutorService service = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 3; i++) {
			service.submit(new ServerExample(countLatch));

		}
		try {
			countLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("All Servres has started successfully. You can submit your job now");

	}
}

class ServerExample implements Runnable {
	private CountDownLatch latch;

	public ServerExample(CountDownLatch latch) {
		this.latch = latch;
	}

	public void run() {
		System.out.println("Server starting");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Server Sarted successfully");
		latch.countDown();
	}
}
