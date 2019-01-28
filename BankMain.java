package poo;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Scanner;

public class BankMain {

	public enum IOCommands {

		OPEN_ACCOUNT("abrir"), CLOSE_ACCOUNT("fechar"), PRINT_ACCOUNT("mostra"), 
		UPDATE_ACCOUNT("movimento"),
		ACCOUNTS_IDS("codigos"), ACCOUNTS("contas"), CLIENTS("clientes"), 
		BALANCES("saldos"), GOLD_ACCOUNTS("maiores"), SCHEDULE_SAVINGS("pagamentos"), 
		SLEEPING_ACCOUNTS("adormecidas"),
		ORDER("ordem"), 
		HELP("ajuda"), END ("sair"),
		BYE ("Adeus!"), PROMPT("Bank>");

		private String name;
		IOCommands (String name) { this.name = name; }
		public String toString() { return this.name; }    

	}; 

	/* 
	 * So far, there is no input validation whatsoever ...
	 * 
	 * Challenge for students:
	 * 
	 *  - To check the program behaviour and then adjust if required
	 * 	- To add exceptions as appropriate
	 *  - To make the program more flexible as far as accounts domain is concerned.
	 *    For instance, to pick up lists of just savings accounts, or else checking
	 *    accounts, or both
	 *  - To add comments and then create a complete Javadoc file
	 *  
	 */
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Bank poorBank = new BankClass("PoorBank");

		uploadBankWithSomeStuff(poorBank);
		System.out.print(IOCommands.PROMPT.toString());
		String command = in.nextLine();
		String [] input = command.split(" ");
		String cmd1 = input[0].trim();
		while ( ! isCommand(cmd1, IOCommands.END) ) {

			if ( isCommand(cmd1, IOCommands.HELP) ) { 
				printHelp();
			}
			else if ( isCommand(cmd1, IOCommands.OPEN_ACCOUNT) ) {
				openAccount(poorBank, input);
			}
			else if ( isCommand(cmd1, IOCommands.CLOSE_ACCOUNT) ) {
				closeAccount(poorBank, input);
			}
			else if ( isCommand(cmd1, IOCommands.PRINT_ACCOUNT) ) {
				printAccount(poorBank, input);					
			}
			else if ( isCommand(cmd1, IOCommands.UPDATE_ACCOUNT) ) {
				updateAccount(poorBank, input);						
			}
			else if ( isCommand(cmd1, IOCommands.ACCOUNTS_IDS) ) {
				printAccountsIds(poorBank);
			}
			else if ( isCommand(cmd1, IOCommands.ACCOUNTS) ) {
				printAccounts(poorBank, input);		
			}
			else if ( isCommand(cmd1, IOCommands.CLIENTS) ) {
				printClients(poorBank, input);
			}
			else if ( isCommand(cmd1, IOCommands.BALANCES) ) {
				printBalances(poorBank, input);			
			}
			else if ( isCommand(cmd1, IOCommands.GOLD_ACCOUNTS) ) {
				printGoldAccounts(poorBank, input);
			}
			else if ( isCommand(cmd1, IOCommands.SCHEDULE_SAVINGS) ) {
				printScheduleSavings(poorBank, input);
			}
			else if ( isCommand(cmd1, IOCommands.SLEEPING_ACCOUNTS) ) {
				printSleepingAccounts(poorBank, input);			
			}

			// looking for the next input
			System.out.print(IOCommands.PROMPT.toString());
			command = in.nextLine();
			input = command.split(" ");
			cmd1 = input[0].trim();
		}

		System.out.println(IOCommands.BYE.toString());
	}


	private static void openAccount(Bank poorBank, String[] input) {

		int inputLength = input.length;
		long idCard = Long.parseLong(input[2].trim());
		long phone = Long.parseLong(input[4].trim());
		GregorianCalendar date = DateSetting(input[5].trim());

		Client client = new ClientClass(input[1].trim(), idCard, input[3].trim(), phone);

		if ( inputLength < 7 ) {
			poorBank.openCheckingAccount(client, date);
		}
		else  {			
			double amount = Double.parseDouble(input[6].trim());
			int duration = Integer.parseInt(input[7].trim());
			double rate = Double.parseDouble(input[8].trim());
			poorBank.openSavingsAccount(client, date, amount, duration, rate);
		}
	}


	private static void closeAccount(Bank poorBank, String[] input) {

		if (poorBank.isThereAccount(input[1].trim())) {
			poorBank.closeAccount(input[1].trim());
		}
	}

	private static void printAccount(Bank poorBank, String[] input) {

		if (poorBank.isThereAccount(input[1].trim())) {
			Account acc = poorBank.getAccount(input[1].trim());
			System.out.println(acc);					
		}	
	}

	private static void updateAccount(Bank poorBank, String[] input) {

		if (poorBank.isThereAccount(input[1].trim())) {
			GregorianCalendar date = DateSetting(input[2].trim());
			double amount = Double.parseDouble(input[3].trim());
			poorBank.updateAccount(input[1].trim(), date, amount);								
		}		

	}


	private static void printAccountsIds(Bank poorBank) {

		System.out.println("Numero de contas existentes: "+poorBank.numberOfAccounts());
		System.out.println("------ Codigos ------" );
		Iterator<String> it = poorBank.accountsIDs();
		printIterator(it);
	}

	private static void printAccounts(Bank poorBank, String[] input) {

		int inputLength = input.length;
		if ( inputLength < 2 ) {					
			Iterator<Account> it = poorBank.accounts();
			printIterator(it);
		}
		else  {				
			long idCard = Long.parseLong(input[2].trim());
			// it doesn't matter address and phone number
			Client client = new ClientClass(input[1].trim(), idCard, "", 0);
			Iterator<Account> it = poorBank.accounts(client);
			printIterator(it);
		}
	}


	private static void printClients(Bank poorBank, String[] input) {

		boolean sorted = false;
		int inputLength = input.length;
		if (inputLength > 1) {
			sorted = isCommand(input[1].trim(), IOCommands.ORDER);
		}
		Iterator<Client> it = poorBank.clients(sorted);
		printIterator(it);
	}


	private static void printBalances(Bank poorBank, String[] input) {

		int inputLength = input.length;
		if ( inputLength < 2 ) {							
			Iterator<?> it = poorBank.totalAmountClients().entrySet().iterator();
			printIterator(it);
		}
		else  {			
			long idCard = Long.parseLong(input[2].trim());
			// it doesn't matter address and phone number
			Client client = new ClientClass(input[1].trim(), idCard, "", 0);
			System.out.println("Balance owned by " + client.getName()+" Card ID = " + client.getCardID() + " : " + poorBank.totalAmountClient(client));	
		}
	}


	private static void printGoldAccounts(Bank poorBank, String[] input) {
		double amount = Double.parseDouble(input[1].trim());
		boolean sorted = false;
		int inputLength = input.length;
		if (inputLength > 2) {
			sorted = isCommand(input[2].trim(), IOCommands.ORDER);
		}	
		printIterator(poorBank.accountsGreaterThan(amount, sorted));
	}


	private static void printScheduleSavings(Bank poorBank, String[] input) {

		GregorianCalendar tillDate = DateSetting(input[1].trim());
		printIterator(poorBank.accountsToPayInterest(tillDate));
	}

	private static void printSleepingAccounts(Bank poorBank, String[] input) {
		GregorianCalendar afterDate = DateSetting(input[1].trim());
		printIterator(poorBank.accountsToPayInterest(afterDate));
	}

	private static GregorianCalendar DateSetting(String date) {
		String [] str = date.trim().split(":");
		int day = Integer.parseInt(str[0]);
		int month = Integer.parseInt(str[1]);
		int year = Integer.parseInt(str[2]);
		return new GregorianCalendar(year, month, day);
	}



	private static boolean isCommand(String str, IOCommands cmd ) {
		return str.equalsIgnoreCase(cmd.toString());
	}

	private static void printHelp() {
		StringBuilder str = new StringBuilder();
		str.append(IOCommands.OPEN_ACCOUNT.toString() + " Snome NBI Sendereco Ntelefone dd:mm:aa + [ Nvalor Nprazo Dtaxa ] \n");
		str.append(IOCommands.CLOSE_ACCOUNT.toString() + " IdConta \n");
		str.append(IOCommands.PRINT_ACCOUNT.toString() + " IdConta \n");
		str.append(IOCommands.UPDATE_ACCOUNT.toString() + " IdConta dd:mm:aa Nvalor\n");
		str.append(IOCommands.ACCOUNTS_IDS.toString() + "\n");
		str.append(IOCommands.ACCOUNTS.toString() + " [ Snome NBI ]\n");
		str.append(IOCommands.CLIENTS.toString() + " [ " + IOCommands.ORDER.toString() + " ]\n");
		str.append(IOCommands.BALANCES.toString() + " [ Snome NBI ]\n");	
		str.append(IOCommands.GOLD_ACCOUNTS.toString() + " [ " + IOCommands.ORDER.toString() + " ]\n");
		str.append(IOCommands.SCHEDULE_SAVINGS.toString() + " dd:mm:aa \n");
		str.append(IOCommands.SLEEPING_ACCOUNTS.toString() + " dd:mm:aa \n");		
		str.append(IOCommands.HELP.toString() + "\n");
		str.append(IOCommands.END.toString() + "\n");
		System.out.println(str);
	}


	private static <E> void printIterator(Iterator<E> it) {
		while (it.hasNext()) {
			System.out.println(it.next());
		}	
	}

	/*
	 * Just to help with some tests I have to carry out
	 */
	private static void uploadBankWithSomeStuff(Bank bank) {

		for(int i=1; i<=20;i++) {
			GregorianCalendar date = new GregorianCalendar();
			date.set(2000+i%6, i%11, i%5);
			Client client = new ClientClass("Cliente"+i, 1000+i, "Endereco"+i, 9999+i);	       
			bank.openCheckingAccount(client, date);
			bank.openSavingsAccount(client, date, i*2, 90, 0.10);
		}

	}


}
