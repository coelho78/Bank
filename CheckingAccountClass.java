package poo;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class CheckingAccountClass extends AbstractAccount implements
		CheckingTransactions {
	
	private List<Transaction> transactions;
	
	public CheckingAccountClass(Client client, GregorianCalendar openingDate) {
		super(client, openingDate, 0);
		transactions = new LinkedList<Transaction>();
	}
	
	public List<Transaction> getTransactions() {		
		return transactions;
	}

	public boolean updateBalance(GregorianCalendar date, double amount) {
		double currentAmount = super.getBalance();
		double nextAmount = currentAmount + amount;
		if (nextAmount < 0) { // sorry not enough money
			return false;
		}
		addTransaction(date, amount);
		super.setLastSettlementDate(date);
		super.setBalance(nextAmount);
		return true;	
	}

	public void addTransaction(GregorianCalendar date, double amount) {
		Transaction tr = new TransactionClass(date, amount);
		transactions.add(tr);
	}

	
	public boolean isCounting(AccountFilter filter) {
		return filter == AccountFilter.CHECKING;
	}

}
