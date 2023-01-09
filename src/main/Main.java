package main;

import bankinternal.Bank;
import bankinternal.Client;

import java.util.ArrayList;

/*************************************************************************************************************************
 *
 * CMSC 22 Object-Oriented Programming
 * Multithreading exercise
 * 
 * (c) Institute of Computer Science, CAS, UPLB
 * 
 *
 *************************************************************************************************************************/
public class Main {
	public static void main(String[] args){
		
		Bank bank = new Bank();
		
		String storeName = bank.createStoreClient("Store #1", 3000);
		bank.createSupplierClient("Supplier #1", 3000, storeName);

		// Get bank threads
		ArrayList<Client> clientThreads = bank.getClientThreads();

		// Start each thread
		for (Client client : clientThreads) {
			client.start();
		}

		// Make sure all threads have finished before printing bank accounts' balance
		for (Client client : clientThreads) {
			try {
				client.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
		bank.printBalance();

	}
}
