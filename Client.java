package poo;

public interface Client extends Comparable<Client> {
	
	String getName();
	long getCardID();
	String getAddress();
	long getPhoneNumber();
	void setAddress(String address);
	void setPhoneNumber(long phoneNumber);
	
}
