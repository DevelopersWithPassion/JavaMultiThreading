package com.chanchal.multithreading.deadlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockSolution2 {
	private Accounts2 account1 = new Accounts2();
	private Accounts2 account2 = new Accounts2();
	private Lock firstLock = new ReentrantLock();
	private Lock secondLock = new ReentrantLock();

	/**
	 * This method helps in preventing deadlock to occur. it first tries to
	 * acquire lock on both object and if both lock get acquired it simply
	 * returns from function. if anyone of lock is already occupied by some
	 * thread that get released
	 */
	public void acquireLock() {
		while (true) {
			boolean isFirstLockAquired = false;
			boolean isSecondLockAquired = false;
			try {
				isFirstLockAquired = firstLock.tryLock();
				isSecondLockAquired = secondLock.tryLock();
			} finally {
				if (isFirstLockAquired && isSecondLockAquired) {
					return;
				}
				if (isFirstLockAquired) {
					firstLock.unlock();
				}
				if (isSecondLockAquired) {
					secondLock.unlock();
				}
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public void tryFirstThread() {
		Random random = new Random();
		for (int i = 0; i < 10000; i++) {
			/**
			 * 
			 */
			acquireLock();

			Accounts2.transfer(account1, account2, random.nextInt(100));

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
			acquireLock();

			Accounts2.transfer(account1, account2, random.nextInt(100));
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

class Accounts2 {
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

	public static void transfer(Accounts2 ac1, Accounts2 ac2, int amount) {
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
