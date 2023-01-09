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
public class SupplierClient extends Client {

	private int amountToPay = 1800;
	private String storeName;
	
	public static final int PAYMENT_COUNT = 2;

	SupplierClient(Bank b, BankAccount account, String name, String storeName){
		this.bank = b;
		this.account = account;
		this.name = name;
		this.storeName = storeName;
	}

	public void run(){
		for (int i = 0; i < PAYMENT_COUNT; i++) {
			bank.requestPayment(this, amountToPay, storeName);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

