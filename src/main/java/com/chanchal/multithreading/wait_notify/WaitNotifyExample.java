package com.chanchal.multithreading.wait_notify;

import java.util.Scanner;

/**
 * The use of the implicit monitors in Java objects is powerful, but you can
 * achieve a more subtle level of control through inter-process communication.
 * As you will see, this is especially easy in Java. Multithreading replaces
 * event loop programming by dividing your tasks into discrete and logical
 * units. Threads also provide a secondary benefit: they do away with polling.
 * Polling is usually implemented by a loop that is used to check some condition
 * repeatedly. Once the condition is true, appropriate action is taken. This
 * wastes CPU time. For example, consider the classic queuing problem, where one
 * thread is producing some data and another is consuming it. To make the
 * problem more interesting, suppose that the producer has to wait until the
 * consumer is finished before it generates more data. In a polling system, the
 * consumer would waste many CPU cycles while it waited for the producer to
 * produce. Once the producer was finished, it would start polling, wasting more
 * CPU cycles waiting for the consumer to finish, and so on. Clearly, this
 * situation is undesirable.
 * 
 * To avoid polling, Java includes an elegant interrocess communication
 * mechanism via the wait( ), notify( ), and notifyAll( ) methods. These methods
 * are implemented as final methods in Object, so all classes have them. All
 * three methods can be called only from within a synchronized method. Although
 * conceptually advanced from a computer science perspective, the rules for
 * using these methods are actually quite simple:
 * 
 * wait( ) tells the calling thread to give up the monitor and go to sleep until
 * some other thread enters the same monitor and calls notify( ). notify( )
 * wakes up the first thread that called wait( ) on the same object. notifyAll(
 * ) wakes up all the threads that called wait( ) on the same object. The
 * highest priority thread will run first. These methods are declared within
 * Objec
 * 
 * 
 * 
 * 
 */
public class WaitNotifyExample {
	public void producer() throws InterruptedException {

		synchronized (this) {
			System.out.println("Producing Something..........");
			wait();
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
