package bankinternal;
/*************************************************************************************************************************
 *
 * CMSC 22 Object-Oriented Programming
 * Multithreading exercise
 * 
 * (c) Institute of Computer Science, CAS, UPLB
 * 
 *
 *************************************************************************************************************************/

/** 
 * No need to change anything here 
 * **/
public abstract class Client extends Thread {
	protected BankAccount account;
	protected String name;
	protected Bank bank;
	
	String getClientName() {
		return this.name;
	}

	BankAccount getAccount() {
		return account;
	}
	
	int getBalance() {
		return this.account.getBalance();
	}
	
	void printBalance() {
		this.account.printBalance();
	}
}
