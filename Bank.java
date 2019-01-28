package poo;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;

public interface Bank {
	
	// the bank
	String getBankName();
	void setBankName(String bankName);
	int numberOfAccounts();

	// the accounts
	void openCheckingAccount(Client client, GregorianCalendar openingDate);
	void openSavingsAccount(Client client, GregorianCalendar openingDate, double amount, int savingDays, double interestRate);
	boolean updateAccount(String accountID, GregorianCalendar date, double amount);
	void closeAccount(String accountID);
	Account getAccount(String accountID);
	boolean isThereAccount(String accountID);
	
	Iterator<String> accountsIDs();
	
	// general information about clients and accounts
	Iterator<Client> clients(boolean ordering);  // clients as a set
	double totalAmountClient(Client client);
	Map<Client, Double> totalAmountClients() ;   // set as a set
	Iterator<Account> accounts();
	Iterator<Account> accounts(Client client);
	Iterator<Account> accountsGreaterThan(double amount, boolean ordering);
	Iterator<Account> accountsToPayInterest(GregorianCalendar tillDate);
	Iterator<Account> sleepingAccounts(GregorianCalendar afterDate);
	

}
