package com.chanchal.multithreading.creationExample;

public class ThreadBasicInfo implements Runnable {

	@Override
	public void run() {
		System.out.println("Executed");
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ThreadBasicInfo info = new ThreadBasicInfo();
		Thread thread = new Thread(info);
		System.out.println(thread.getName());
		System.out.println(thread.getId());
		thread.setPriority(1);
		thread.setName("infoThread");
		System.out.println(thread.getPriority());
		System.out.println(thread.getName());

		
		
		Thread currentThread=Thread.currentThread();
		System.out.println(currentThread.getName());

	}

}
