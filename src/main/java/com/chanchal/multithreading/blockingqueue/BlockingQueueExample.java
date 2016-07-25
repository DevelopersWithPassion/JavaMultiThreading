package com.chanchal.multithreading.blockingqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class shows example of BlockingQueue
 * 
 * BlockingQueue is a unique collection type which not only store elements but
 * also supports flow control by introducing blocking if either BlockingQueue is
 * full or empty. take() method of BlockingQueue will block if Queue is empty
 * and put() method of BlockingQueue will block if Queue is full. This property
 * makes BlockingQueue an ideal choice for implementing Producer consumer design
 * pattern where one thread insert element into BlockingQueue and other thread
 * consumes it.
 * 
 *
 * There can be many creative usage of BlockingQueue in Java given its flow
 * control ability. Two of the most common ways I see programmer uses
 * BlockingQueue is to implement Producer Consumer design pattern and
 * implementing Bounded buffer in Java. It surprisingly made coding and inter
 * thread communication over a shared object very easy.
 * 
 * Few Important Points to note
 * 
 * 1) BlockingQueue in Java doesn't allow null elements, various implementation
 * of BlockingQueue like ArrayBlockingQueue, LinkedBlockingQueue throws
 * NullPointerException when you try to add null on queue.
 * 
 * 2) BlockingQueue implementations like ArrayBlockingQueue, LinkedBlockingQueue
 * and PriorityBlockingQueue are thread-safe. All queuing method uses
 * concurrency control and internal locks to perform operation atomically. Since
 * BlockingQueue also extend Collection, bulk Collection operations like
 * addAll(), containsAll() are not performed atomically until any BlockingQueue
 * implementation specifically supports it. So call to addAll() may fail after
 * inserting couple of elements.
 * 
 * 
 * 3) Common methods of BlockingQueue is are put() and take() which are blocking
 * methods in Java and used to insert and retrive elements from BlockingQueue in
 * Java. put() will block if BlockingQueue is full and take() will block if
 * BlockingQueue is empty, call to take() removes element from head of Queue
 **/

public class BlockingQueueExample {
	private static BlockingQueue<Employee> employeeQueue = new ArrayBlockingQueue<>(10);

	private static void addEmployeeDetail() throws InterruptedException {
		Random random = new Random();
		for (int i = 0; i < 100; i++) {
			Thread.sleep(100);
			employeeQueue.put(new Employee(random.nextInt(100)));

		}

	}

	private static void getEmployeesDetail() throws InterruptedException {
		Random random = new Random();

		while (true) {
			Thread.sleep(10);
			if (random.nextInt(10) == 0) {
				Employee emp = employeeQueue.take();
				System.out
						.println("Taken Employee with Id :" + emp.getId() + " new Queue Size :" + employeeQueue.size());
			}
		}
	}

	public static void main(String[] args) {
		Thread producer = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					addEmployeeDetail();
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
					getEmployeesDetail();
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

class Employee {
	private int id;

	public Employee(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
