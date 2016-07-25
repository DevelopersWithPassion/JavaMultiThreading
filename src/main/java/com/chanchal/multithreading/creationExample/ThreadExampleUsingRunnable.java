package com.chanchal.multithreading.creationExample;

public class ThreadExampleUsingRunnable implements Runnable {

	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ThreadExampleUsingRunnable example = new ThreadExampleUsingRunnable();
		Thread t1 = new Thread(example);
		t1.start();
		ThreadExampleUsingRunnable example2 = new ThreadExampleUsingRunnable();
		Thread t2 = new Thread(example2);
		t2.start();
	}
}
