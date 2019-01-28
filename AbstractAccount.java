package poo;



import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class AbstractAccount implements Account {
	
	private static long seed = 0;

	private String accountID;
	private Client client;
	private GregorianCalendar openingDate;
	private GregorianCalendar lastSettlementDate;
	private double balance;
	
	public AbstractAccount(Client client, GregorianCalendar openingDate, double amount) {
		this.accountID = generateAccountID();
		this.client = client;	
		int day = openingDate.get(Calendar.DAY_OF_MONTH);
		int month = openingDate.get(Calendar.MONTH);
		int year = openingDate.get(Calendar.YEAR);
		this.openingDate = new GregorianCalendar(year,month,day);
		this.lastSettlementDate = new GregorianCalendar(year,month,day);
		this.balance = amount;
		
	}
	public String getAccountID() {	
		return accountID;
	}
	
	public double getBalance() {
		return balance;
	}

	
	public Client getClient() {	
		return client;
	}

	public GregorianCalendar getOpeningDate() {
		return openingDate;
	}
	
	public GregorianCalendar getLastSettlementDate() {
		return lastSettlementDate;
	}

	
	public void setLastSettlementDate(GregorianCalendar date) {
		lastSettlementDate = date;
	}
	
	public void setBalance(double amount) {
		balance = amount;
	}
	
	public abstract boolean updateBalance(GregorianCalendar date, double amount);
	
	// ... can be another mechanism to filter the listing of accounts
	// then I can add extra (similar) methods in the bank just to look at only those
	public abstract boolean isCounting(AccountFilter filter); 
	
	private static String generateAccountID() {
		seed++;
		return seed + "";
	}
	
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\n----------  Conta com codigo: " + accountID + "  ------------");
		str.append(client.toString()+"\n\n");
		int day = openingDate.get(GregorianCalendar.DAY_OF_MONTH);
		int month = openingDate.get(GregorianCalendar.MONTH);
		int year = openingDate.get(GregorianCalendar.YEAR);
		str.append("Abertura: " + day  + "/" + month +  "/" + year + "\t");
		day = lastSettlementDate.get(GregorianCalendar.DAY_OF_MONTH);
		month = lastSettlementDate.get(GregorianCalendar.MONTH);
		year = lastSettlementDate.get(GregorianCalendar.YEAR);
		str.append("Ultimo movimento: " + day  + "/" + month +  "/" + year + "\t");
		String balance = String.format("%10.2f", this.balance);
		str.append("Saldo: " + balance + "\t");

		return str.toString();
	}

}
