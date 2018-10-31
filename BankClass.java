package poo;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class BankClass implements Bank {

	private Map<String, Account> accounts;
    private String bankName;
    
    public BankClass(String bankName) {
    	this.accounts = new HashMap<String, Account>();
    	this.bankName = bankName;
    }
    
    public String getBankName() {
		return this.bankName;
	}
    
    public void setBankName(String bankName) {
		this.bankName = bankName;	
	}
    
	public int numberOfAccounts() {
		return accounts.size();
	}
	
	public void openSavingsAccount(Client client,
			GregorianCalendar openingDate, double amount, int savingDays,
			double interestRate) {
		Account acc = new SavingsAccountClass(client, openingDate, amount, 
				savingDays, interestRate);
		accounts.put(acc.getAccountID(), acc);
	}
	
	public void openCheckingAccount(Client client, GregorianCalendar openingDate) {
		Account acc = new CheckingAccountClass(client, openingDate);
		accounts.put(acc.getAccountID(), acc);
	}
	
	public boolean updateAccount(String accountID,
			GregorianCalendar date, double amount) {
		Account acc = accounts.get(accountID);
		return acc.updateBalance(date, amount);
	}

	public void closeAccount(String accountID) {
		accounts.remove(accountID);
	}

	public Account getAccount(String accountID) {
		return accounts.get(accountID); 
	}
	
	public boolean isThereAccount(String accountID) {
		return accounts.containsKey(accountID);
	}
	
	public Iterator<String> accountsIDs() {
		return accounts.keySet().iterator();
	}
	
	public Iterator<Client> clients(boolean ordering) {
		Set<Client> clients;
		if (ordering) {
			clients = new TreeSet<Client>();
		}
		else {
			clients = new HashSet<Client>();
		}
		for (Account acc : accounts.values()) {
    		clients.add(acc.getClient());	
    	}
    	return clients.iterator();
		
	}

	public double totalAmountClient(Client client) {
		double total = 0;
		for (Account acc : accounts.values()) {
			if (acc.getClient().compareTo(client)==0) {
				total += acc.getBalance();
			}
		}
		return total;
	}
	
	public Map<Client, Double> totalAmountClients() {
		Map<Client, Double> amountClients = new HashMap<Client, Double>();
		Set<Client> clients = new HashSet<Client>();
		for (Account acc : accounts.values()) {
			clients.add(acc.getClient());
		}
		for (Client client : clients) {
			amountClients.put(client, totalAmountClient(client));
		}		
		return amountClients;
	}
	
	public Iterator<Account> accounts() {
		return accounts.values().iterator();
	}

	
	public Iterator<Account> accounts(Client client) {
		Set<Account> accounts = new HashSet<Account>();
		for (Account acc : this.accounts.values()) {
			if (acc.getClient().compareTo(client)==0) {
				accounts.add(acc);
			}		
		}		
		return accounts.iterator();
	}


	public Iterator<Account> accountsGreaterThan(double amount, boolean ordering) {
		Set<Account> accs;
		if (ordering) {
			accs = new TreeSet<Account>(new AccountComparatorByBalance<Account>());
		}
		else {
			accs = new HashSet<Account>();
		}
		
		for(Account acc : accounts.values()) {
			if (acc.getBalance() > amount) {
				accs.add(acc);
			}
		}
		return accs.iterator();
	}

   
	public Iterator<Account> accountsToPayInterest(GregorianCalendar tillDate) {
		Set<Account> accs = new HashSet<Account>();
		for (Account acc : accounts.values()) {
			if (acc.isCounting(AccountFilter.SAVINGS)) {
				GregorianCalendar date = acc.getLastSettlementDate();
				long oneHour = 3600000L;	
				long daysDifference = (tillDate.getTimeInMillis() - date.getTimeInMillis()) / (oneHour * 24);
				if (daysDifference > ((Savings) acc).getSavingDays()) {
					accs.add(acc);
				}
			}
		}
		return accs.iterator();
	}
	
	

	public Iterator<Account> sleepingAccounts(GregorianCalendar afterDate) {
		Set<Account> accs = new HashSet<Account>();
		for (Account acc : accounts.values()) {
			if (acc.isCounting(AccountFilter.CHECKING)) {
				if (acc.getLastSettlementDate().after(afterDate) ) {
					accs.add(acc);
				}	
			}
		}
		return accs.iterator();
	}
	

//	
//	private void printDate(GregorianCalendar date) {
//		int day = date.get(GregorianCalendar.DAY_OF_MONTH);
//		int month = date.get(GregorianCalendar.MONTH);
//		int year = date.get(GregorianCalendar.YEAR);
//		System.out.println("Data: " + day  + "/" + month +  "/" + year + "\t");
//	}
	

}
