package poo;

public class ClientClass implements Client {

	private String name;
	private long cardID;
	private String address;
	private long phoneNumber;
	
	public ClientClass(String name, long cardID, String address, long phoneNumber) {
		this.name = name;
		this.cardID = cardID;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	public String getName() { 
		return name;
	}
	
	public long getCardID() {
		return cardID;
	}
	
	public String getAddress() {		
		return address;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setAddress(String address) {		
		this.address = address;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public int compareTo(Client other) {
		String c1 = this.getName() + this.getCardID();
		String c2 = other.getName() + other.getCardID();
		return c1.compareToIgnoreCase(c2);
	}
	
	/*
	 * PS. Change hashCode() accordingly if required
	 */
	public boolean equals(Client other) {
		return this.compareTo(other)==0;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
        str.append("\n------------ Cliente ------------\n");
        str.append("Nome: " + name + "\t\t");
        str.append("BI: " + cardID + "\n");
        str.append("Endereco: " + address + "\t");
        str.append("Telefone: " + phoneNumber + "\t");
        return str.toString();
	}

}
