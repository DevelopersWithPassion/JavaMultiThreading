package com.chanchal.multithreading.wait_notify;

import java.util.Scanner;

public class SpinLockExample {
	private boolean spinLockMonitor = false;

	public void producer() throws InterruptedException {

		synchronized (this) {
			System.out.println("Producing Something..........");
			while (!spinLockMonitor) {
				wait();
			}
			System.out.println("Producer has Resumed its task");
		}
	}

	public void consumer() throws InterruptedException {
		Thread.sleep(2000);
		Scanner sc = new Scanner(System.in);
		synchronized (this) {
			System.out.println("Consumed");
			System.out.println("Waiting for somone to press enter");
			sc.nextLine();
			System.out.println("You have Pressed Key. Producer will resume its task now");
			notify();
			spinLockMonitor = true;
		}
	}

	public static void main(String[] args) {
		final WaitNotifyExample example = new WaitNotifyExample();
		Thread producer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					example.producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		Thread consumer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					example.consumer();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		producer.start();
		consumer.start();

		try {
			producer.join();
			consumer.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
