package poo;

import java.util.GregorianCalendar;

public class SavingsAccountClass extends AbstractAccount implements
		Savings {

	private int savingDays;
	private double interestRate;

	public SavingsAccountClass(Client client, GregorianCalendar openingDate, double amount, int savingDays, double interestRate) {
	
		super(client, openingDate, amount);
		this.savingDays = savingDays;
		this.interestRate = interestRate;
	}
	
	public int getSavingDays() {
		return savingDays;
	}
	
	public double getAnnualInterestRate() {
		return interestRate;
	}
	

	private static long daysBetween(GregorianCalendar from, GregorianCalendar to) {
		long oneHour = 3600000L;
		return ( (to.getTimeInMillis() - from.getTimeInMillis() ) / (oneHour * 24));
	}

	
	public boolean updateBalance(GregorianCalendar date, double amount) {
		GregorianCalendar lastDate = super.getLastSettlementDate();
		if (daysBetween(lastDate,date) == savingDays ) {
			double currentBalance = super.getBalance();
			double interest = currentBalance * (savingDays / Savings.PAY_DAYS_YEAR) * interestRate;
			double nextBalance = currentBalance + interest + amount; 
			// since amount can be negative
			if (nextBalance > 0) {
				super.setLastSettlementDate(date);
				super.setBalance(nextBalance);
				return true;		
			}
		}
		return false;
	}

	
	public boolean isCounting(AccountFilter filter) {
		return filter == AccountFilter.SAVINGS;
	}

	
	  
	
}
