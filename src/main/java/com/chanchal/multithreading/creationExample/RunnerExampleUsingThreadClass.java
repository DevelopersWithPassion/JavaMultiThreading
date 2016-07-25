package com.chanchal.multithreading.creationExample;

public class RunnerExampleUsingThreadClass extends Thread {
	@Override
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
		RunnerExampleUsingThreadClass exp = new RunnerExampleUsingThreadClass();
		exp.start();

		RunnerExampleUsingThreadClass exp1 = new RunnerExampleUsingThreadClass();

		exp1.start();

	}
}
