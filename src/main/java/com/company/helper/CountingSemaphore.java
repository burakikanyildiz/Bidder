package com.company.helper;
//Burak İkan Yıldız //2015400069 //burakikanyildiz@gmail.com
//CMPE436-Project

public class CountingSemaphore {

	int value;

	public CountingSemaphore(int initValue) {
		this.value = initValue;
	}

	// Takes resource
	public synchronized void P() {
		while (this.value == 0)
			try {
				wait();
			} catch (InterruptedException e) {
			}
		this.value--;
	}

	// Releases resource
	public synchronized void V() {
		this.value++;
		notify();
	}
	

}


