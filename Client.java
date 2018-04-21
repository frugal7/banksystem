import java.util.Locale;
import java.text.DecimalFormat;
import java.math.BigDecimal;
//import java.io.*;

class Client implements java.io.Serializable //extends AllClients		//moge utworzyc interfejs Client, a tę klasę nazwać OutClient, serializable to 
//interfejs znacznikowy
{

	static final DecimalFormat formatToPln = new DecimalFormat("$0.00");
	//private static int lastId = 0;


	private String name;
	private String surname;
	private long pesel;		//sprawdz czy zgadza sie liczbA cyfr
	private int clientId;
	private double accountBalance;
	private String address;	

	public Client(String name, String surname, long pesel, String address, int clientId)	
	{
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
		this.accountBalance = 0.0;
		this.address = address;
		//lastId++;
		this.clientId = clientId;
		//this.accountBalance = new BigDecimal(0);
	}

	public void printUsersInfo()
	{
		System.out.println("\n\n*************************************************\n" +
			"Info about client number " + Integer.toString(this.clientId) +
			"\nName: " + this.name + 
			"\nSurname: " + this.surname + 
			"\nPesel: " + Long.toString(this.pesel) +
			"\nAddress: " + this.address +
			"\nAccount balance: " + formatToPln.format(this.accountBalance) +
			"\n*************************************************\n\n");
			
	}



	public int getClientId()
	{
		return this.clientId;
	}

	public String getClientName()
	{
		return this.name;
	}

	public String getClientSurname()
	{
		return this.surname;
	}

	public long getClientPesel()
	{
		return this.pesel;
	}

	public String getClientAddress()
	{
		return this.address;
	}


	public void deposit(double amount)
	{
		this.accountBalance = this.accountBalance + amount;
	}


	public boolean withdraw(double amount)
	{
		if(this.accountBalance - amount < 0)
		{
			return false;
		}
		else
		{
			this.accountBalance = this.accountBalance - amount;
			return true;
		}

	}


/*
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException 
    {
    	stream.defaultReadObject();
    	lastId = stream.readInt();
    }


    private void writeObject(ObjectOutputStream stream) throws IOException 
    {
    	stream.defaultWriteObject();
        stream.writeInt(lastId);
    }*/
}