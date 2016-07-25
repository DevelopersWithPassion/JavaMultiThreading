package com.chanchal.multithreading.graceshutdown;

import java.util.Scanner;

public class ThreadShutDownExample extends Thread {
	private volatile boolean isTrue = true;

	@Override
	public void run() {
		while (isTrue) {
			System.out.println("Hello I Am running");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopThread() {
		isTrue = false;
	}

	public static void main(String[] args) {
		ThreadShutDownExample thread = new ThreadShutDownExample();
		thread.start();

		Scanner sc = new Scanner(System.in);
		System.out.println("Hit Key to stop execution");
		sc.nextLine();

		thread.stopThread();

	}
}
