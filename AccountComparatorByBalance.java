package poo;

import java.util.Comparator;

public class AccountComparatorByBalance<E extends Account> implements Comparator<Account> {
	
	public int compare(Account acc1, Account acc2) {
		double diff = acc1.getBalance()-acc2.getBalance();
	    return (diff > 0 ? 1 : (diff < 0 ? -1 : 0)); 	
	}

}
