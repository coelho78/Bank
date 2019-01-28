package poo;

import java.util.GregorianCalendar;
import java.util.List;

public interface CheckingTransactions {
	List<Transaction> getTransactions();
	void addTransaction(GregorianCalendar date, double amount);
}
