package com.chanchal.multithreading.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class contains Solution for deadlock problem explain in class
 * DeadLockProblemExample . Lock is being release and acquire in same order.
 * 
 * 
 */
public class DeadLockSolution1 {
	private Accounts account1 = new Accounts();
	private Accounts account2 = new Accounts();
	private Lock firstLock = new ReentrantLock();
	private Lock secondLock = new ReentrantLock();

	public void tryFirstThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			/**
			 * firstLock and secondLock is acquired in this method. it has to be
			 * acquired in same order in other method as well otherwise you will
			 * be facing cyclic dependency and deadlock will occur.
			 */
			firstLock.lock();
			secondLock.lock();
			try {

				Accounts.transfer(account1, account2, random.nextInt(100));

			} finally {
				firstLock.unlock();
				secondLock.unlock();
			}
		}
	}

	public void secondThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			/**
			 * firstLock and secondLock is acquired in this method. it has to be
			 * acquired in same order in other method as well otherwise you will
			 * be facing cyclic dependency and deadlock will occur.
			 */
			firstLock.lock();
			secondLock.lock();
			try {

				Accounts.transfer(account2, account1, random.nextInt(100));

			} finally {
				firstLock.unlock();
				secondLock.unlock();
			}
		}
	}

	public void finalBalance() {
		System.out.println("Account1 balance :" + account1.getBalance());
		System.out.println("Account2 balance :" + account2.getBalance());
		System.out.println("Total balance :" + (account1.getBalance() + account2.getBalance()));

	}

	public static void main(String[] args) {

	}

}

class Accounts {
	private int balance = 25000;

	public int deposit(int amount) {
		this.balance += amount;
		return balance;

	}

	public int withdraw(int amount) {
		this.balance -= amount;
		return balance;
	}

	public int getBalance() {
		return balance;
	}

	public static void transfer(Accounts ac1, Accounts ac2, int amount) {
		ac1.balance -= amount;
		ac2.balance += amount;
	}

	public static void main(String[] args) {
		final DeadLockSolution1 example = new DeadLockSolution1();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				example.tryFirstThread();
			}
		});
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				example.secondThread();
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		example.finalBalance();

	}
}
