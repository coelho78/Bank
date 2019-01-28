package poo;

import java.util.GregorianCalendar;

public class TransactionClass implements Transaction {
 
	private GregorianCalendar date;
	private double value;
	
	public TransactionClass(GregorianCalendar date, double value) {
		this.date = date;
		this.value = value;
	}
	
	public GregorianCalendar getDate() {
		return date;
	}

	public double getValue() {	
		return value;
	}

	public boolean isDeposit() {
		return value > 0;
	}

}
