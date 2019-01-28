package poo;

import java.util.GregorianCalendar;

public interface Account {
	
	String getAccountID();
	double getBalance();
	Client getClient();
	GregorianCalendar getOpeningDate();
	GregorianCalendar getLastSettlementDate();
	void setLastSettlementDate(GregorianCalendar date);
	void setBalance(double amount);
	boolean updateBalance(GregorianCalendar date, double amount);
	
	boolean isCounting(AccountFilter filter);
}
