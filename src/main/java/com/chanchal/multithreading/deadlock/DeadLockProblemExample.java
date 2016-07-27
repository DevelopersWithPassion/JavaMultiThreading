package com.chanchal.multithreading.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class shows basic example of how deadlock occurs.in example you can see
 * thread t1 has acquired firstLock on object and thread2 has acquired lock on
 * secondLock. now thread t2 is waiting for firstLock to get free and Thread t1
 * is waiting for secondLock to be free.
 * 
 * 
 * 
 */
public class DeadLockProblemExample {
	private Account account1 = new Account();
	private Account account2 = new Account();
	private Lock firstLock = new ReentrantLock();
	private Lock secondLock = new ReentrantLock();

	public void tryFirstThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			firstLock.lock();
			secondLock.lock();
			try {

				Account.transfer(account1, account2, random.nextInt(100));

			} finally {
				firstLock.unlock();
				secondLock.unlock();
			}
		}
	}

	public void secondThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			secondLock.lock();
			firstLock.lock();
			try {

				Account.transfer(account2, account1, random.nextInt(100));

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

class Account {
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

	public static void transfer(Account ac1, Account ac2, int amount) {
		ac1.balance -= amount;
		ac2.balance += amount;
	}

	public static void main(String[] args) {
		final DeadLockProblemExample example = new DeadLockProblemExample();

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
