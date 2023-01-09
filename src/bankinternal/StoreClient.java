package bankinternal;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*************************************************************************************************************************
 *
 * CMSC 22 Object-Oriented Programming
 * Multithreading exercise
 * 
 * (c) Institute of Computer Science, CAS, UPLB
 * 
 *
 *************************************************************************************************************************/
public class StoreClient extends Client {

	private int bankDepositAmount = 350;
	private int bankWithdrawAmount = 350;
	
	public static final int CYCLE_COUNT = 50;
	
	StoreClient(Bank b, BankAccount account, String name){
		this.bank = b;
		this.account = account;
		this.name = name;
	}

	public void run(){
		Random rand = new Random();
		for (int i = 0; i < CYCLE_COUNT; i++) {
			int choice = rand.nextInt(2);
			if (choice == 0) {
				bank.processDeposit(this, bankDepositAmount);
			} else {
				bank.processWithdraw(this, bankWithdrawAmount);
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("\nStore account's deposit-withdraw cycle has ended.\n");

		account.setEndOfCycle(true);

		synchronized(account) {
			account.notifyAll();
		}
	}
	
}

