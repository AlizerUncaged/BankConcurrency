package bankinternal;
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

public class Bank {

	private ArrayList<Client> clientList;			// keep a list of all client accounts, regardless of type
	private static int numberOfAccounts;

	public Bank(){
		clientList = new ArrayList<Client>();
	}
	
	public String createStoreClient(String storeName, int storeBalance) {

		BankAccount account = new BankAccount(storeName, ++Bank.numberOfAccounts, storeBalance, this);
		StoreClient store = new StoreClient(this, account, storeName);
		clientList.add(store);

		return storeName;
	}
	
	public void createSupplierClient(String supplierName, int supplierBalance, String storeName) {

		BankAccount account = new BankAccount(supplierName, ++Bank.numberOfAccounts, supplierBalance, this);
		SupplierClient supplier = new SupplierClient(this, account, supplierName, storeName);
		clientList.add(supplier);
	}
	
	public int getNumberofClients() {
		return this.clientList.size();
	}

	public ArrayList<Client> getClientThreads() {
		return clientList;
	}

	Client findClient(String name) {
		for (Client c: clientList) {
			if (c.getClientName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public void processDeposit(StoreClient store, int amountToDeposit) {
		BankAccount account = store.getAccount();
		account.deposit(amountToDeposit);
	}

	public void processWithdraw(StoreClient store, int amountToWithdraw) {

		/**
		 * this function checks if the store client's account has enough balance to cover the withdrawal. If it does,
		 * it calls the withdraw method of the BankAccount class to update the balance. If not, it prints a message
		 * indicating that the withdrawal failed due to insufficient balance.
		 *
		 * Note that this implementation does not handle the case where the store client has a pending payment request
		 * from the supplier client. To handle this case, you can add a check to see if the store client has a pending
		 * payment, and if it does, return without processing the withdrawal request. You can also add a boolean flag
		 * in the StoreClient class to indicate whether the store has a pending payment, and set this flag to true when
		 * a payment request is made, and to false when the payment is processed or when the deposit-withdraw cycle
		 * finishes.
		 */
		BankAccount account = store.getAccount();
		if (account.getBalance() >= amountToWithdraw) {
			// there is enough money in the account to process the withdrawal
			account.withdraw(amountToWithdraw);
		} else {
			System.out.println("Withdrawal failed: not enough balance in " + store.getClientName() + "'s account");
		}
	}

	public void requestPayment(SupplierClient supplier, int amountToPay, String storeName) {
		// Find the store client's account
		Client store = findClient(storeName);
		if (store == null) {
			System.out.println("Payment failed: store not found");
			return;
		}
		BankAccount storeAccount = store.getAccount();

		// Check if the store has sufficient balance to pay the supplier
		if (storeAccount.pay(amountToPay, supplier)) {
			// Payment was successful, update the balances of the store and supplier accounts
			storeAccount.withdraw(amountToPay);
			BankAccount supplierAccount = supplier.getAccount();
			supplierAccount.remit(amountToPay);
		} else {
			// Payment failed because the store has insufficient balance
			System.out.println("Payment failed: not enough balance in " + storeName + "'s account");
		}
	}

	public void completePayment(int amountToPay, SupplierClient supplier) {
		// Find the supplier's account and update its balance
		BankAccount supplierAccount = supplier.getAccount();
		supplierAccount.remit(amountToPay);

	}

	public void printBalance() {
		System.out.println("============================\nBank account information:\n");
		for (Client c : clientList) {
			System.out.println(c.getClientName() + ": " + c.getAccount().getBalance());
		}
		System.out.println("============================");
	}

//	Will need this method to do the bonus
//	public void endCycle() {
//		for (Client c : clientList) {
//			c.getAccount().notifyThreads();
//		}	
//	}
}
