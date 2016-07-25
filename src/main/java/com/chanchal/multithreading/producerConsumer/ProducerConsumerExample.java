package com.chanchal.multithreading.producerConsumer;

import java.util.LinkedList;
import java.util.Random;

/**
 * This class shows ProducerConsumer Example Using wait and notify. program does
 * not allow size of LinkedList to exceed ThreshHold.Producer has to wait if
 * size reaches maximum threshold. consumer has to wait when LinkedList is
 * empty. Remember producer notify consumer after putting data into list and
 * consumer notify producer after consuming data from it. 
 * 
 * 
 * 
 * 
 **/
public class ProducerConsumerExample {
	private LinkedList<Integer> intList = new LinkedList<>();
	private final int THRESHOLD = 10;
	private Object lock = new Object();

	public void producer() throws InterruptedException {
		int value = 0;
		while (true) {
			synchronized (lock) {
				while (intList.size() == THRESHOLD) {
					lock.wait();

				}
				intList.add(value++);
				lock.notify();

			}
		}
	}

	public void consumer() throws InterruptedException {
		Random sleepTime = new Random();
		while (true) {
			synchronized (lock) {
				while (intList.size() == 0) {
					lock.wait();
				}
				System.out.println("Reading from LinkedList ");
				int value = intList.removeFirst();
				System.out.println("Value from producer is " + value);
				lock.notify();
			}
			Thread.sleep(sleepTime.nextInt(1000));
		}

	}

	public static void main(String[] args) {
		final ProducerConsumerExample example = new ProducerConsumerExample();
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
