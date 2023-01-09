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
 * TO DO:
 * - Check relevant methods and see which will need the 'synchronized' keyword;
 * 
 * - (bonus): which method will need to notify threads?
 */

class BankAccount {
	private int balance;
	private int accountNum;
	private String name;
	private Bank bank;
	
	private boolean hasPending = false;	// to prevent withdrawing when there's pending payment
	private boolean endOfCycle = false; // bonus: to prevent supplier thread from waiting when store is finished with its cycle

	public void setEndOfCycle(boolean endOfCycle)
	{
		this.endOfCycle = endOfCycle;
	}
	
	BankAccount(String name, int accountNum, int balance, Bank b){ 
		this.name = name;
		this.accountNum = accountNum;
		this.balance = balance;
		System.out.println(this.name + " Initial bank account balance: " + this.balance);
		this.bank = b;
	}

	boolean withdraw(int x){

		if (this.balance < x) {
			System.out.println("Cannot withdraw from " + this.name + ", insufficient balance.");
			return false;
		}
		if (this.hasPending) {
			System.out.println("Pending payment for " + this.name + ". Withdrawal request denied.");
			return false;
		}

		this.balance -= x;
		return true;
	
		
	}

	boolean pay(int amountToPay, SupplierClient supplier) {
		if (this.balance < amountToPay) {
			this.hasPending = true;
			System.out.println("Insufficient balance for " + this.name + " to make payment of " + amountToPay + " to " + supplier.getClientName() + ". Payment request denied.");
			return false;
		}
		if (this.hasPending) {
			System.out.println("Pending payment for " + this.name + ". Payment request denied.");
			return false;
		}
		hasPending = false;
		this.balance -= amountToPay;
		System.out.println("Payment completed: " + amountToPay + " from " + this.name + " to " + supplier.getClientName());

		bank.completePayment(amountToPay, supplier);
		return true;
	}
	
	void deposit(int amount){  
		this.balance+=amount;
		System.out.println(amount + " deposited to " + this.name);
	}
	
	void remit(int amount){  
		this.balance+=amount;
		System.out.println(amount + " remitted to " + this.name);
	}
	
	int getBalance(){ 
		return this.balance;
	}
	
	void printBalance(){  
		System.out.println(this.name + " has " + this.getBalance());
	}	


}
