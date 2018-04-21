import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class AllClients implements java.io.Serializable
{

	private ArrayList<Client> allClients;// = new ArrayList();


	private int lastId;

	public AllClients()
	{
		lastId = 0;
		allClients = new ArrayList();
	}


	public void addClient(Client newClient)
	{
		allClients.add(newClient);
		lastId++;
	}

	public void printAllClients()
	{
		for(int i = 0; i < allClients.size(); i++)
		{
			Client client = allClients.get(i);
			client.printUsersInfo();
		}
	}



	public boolean isEmpty()
	{
		return allClients.isEmpty();
	}



	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException 
    {
    	stream.defaultReadObject();
    	lastId = stream.readInt();
    }


    private void writeObject(ObjectOutputStream stream) throws IOException 
    {
    	stream.defaultWriteObject();
        stream.writeInt(lastId);
    }

    public int getLastId()
    {
    	return this.lastId;
    }


    public boolean deleteAccount(int id)
    {
    	if(this.isEmpty())
    	{
    		return false;
    	}
    	else 
    	{

    		int numberOfClient = findPostionOfClientById(id);

    		if(numberOfClient == -1)
    		{
    			return false;
    		}
    		else
    		{
				allClients.remove(numberOfClient);
				return true;
    		}


    		/*
    		for(int i =0; i < allClients.size(); i++)
    		{
    			Client client = allClients.get(i);

    			if(client.getClientId() == id)
    			{
					allClients.remove(i);
					return true;	
    			}
    		}*/
    	}

    	//return false;
    }




    private int findPostionOfClientById(int id)
    {
    	    for(int i = 0; i < allClients.size(); i++)
    		{
    			Client client = allClients.get(i);

    			if(client.getClientId() == id)
    			{
					return i;
    			}
    		}
    		return -1;
    }


/*
    private boolean askIfUserIsSure()
    {
		Scanner reader = new Scanner(System.in);
    	System.out.println("Do you really want to do this operation? [y/n]");
    	String word = reader.nextLine().toString();
		if(word.lenght() != 1)
		{
    		System.out.println("Please enter correct letter: ");
			return false;
		}

	//	Matcher matcher;
		Pattern compiledPattern = Pattern.compile("[^yn]");
		Matcher matcher = compiledPattern.matcher(word);

		while(word.length() != 1 || matcher.find())
		{
			System.out.print("Please enter correct letter: ");
			word = reader.nextLine().toString();
			matcher = compiledPattern.matcher(word);
		}	


		if(word.charAt(0) == 'y')
		{
			return true;
		}
		else
		{
			return false;
		}
		
    }

*/
   	public boolean deposit(int id, double amount)
   	{
   		if(this.isEmpty() || amount <= 0)
    	{
    		return false;
    	}
    	else 
    	{

    		int numberOfClient = findPostionOfClientById(id);

    		if(numberOfClient == -1)
    		{
    			return false;
    		}
    		else
    		{
				Client client = allClients.get(numberOfClient);
				client.deposit(amount);
				return true;
    		}


    		/*
    		for(int i =0; i < allClients.size(); i++)
    		{
    			Client client = allClients.get(i);

    			if(client.getClientId() == id)
    			{
					allClients.remove(i);
					return true;	
    			}
    		}*/
    	}
   	}

   	public boolean withdraw(int id, double amount)
   	{
   		if(this.isEmpty() || amount <= 0)
    	{
    		return false;
    	}
    	else 
    	{

    		int numberOfClient = findPostionOfClientById(id);

    		if(numberOfClient == -1)
    		{
    			return false;
    		}
    		else
    		{
				Client client = allClients.get(numberOfClient);

				if(client.withdraw(amount))
				{
					return true;
				}
				else
				{
					return false;
				}
    		}
    	}
   	}

   	public boolean transferMoney(int senderId, int receiverId, double amount)
   	{
   		if(this.isEmpty() || amount <= 0 || senderId == receiverId)
    	{
    		return false;
    	}
    	else 
    	{
    		int numberOfSender = findPostionOfClientById(senderId);
    		int numberOfReceiver = findPostionOfClientById(receiverId);

    		if(numberOfSender == -1 || numberOfReceiver == -1)
    		{
    			return false;
    		}
    		else
    		{
    			Client sender = allClients.get(numberOfSender);
    			Client receiver = allClients.get(numberOfReceiver);

    			if(sender.withdraw(amount))
    			{
    				receiver.deposit(amount);
    				allClients.add(numberOfSender, sender);
    				allClients.add(numberOfReceiver, receiver);
    				return true;
    			}
    			else
    				return false;
    		}
    	}
   	}

   	public void printUserById(int id)
   	{
   		int position = findPostionOfClientById(id);

   		if(position == -1)
   		{
   			System.out.println("User doesn't exist!");
   		}
   		else
   		{
   			Client client = allClients.get(position);
   			client.printUsersInfo();
   		}
   	}

   	public void printUsersByName(String name)
   	{
   		boolean flag = true;
	    for(int i = 0; i < allClients.size(); i++)
		{
			Client client = allClients.get(i);
			String clientName = client.getClientName();

			if(clientName.equalsIgnoreCase(name))
			{
				client.printUsersInfo();
				flag = false;
			}
		}

		if(flag)
		{
			System.out.println("User doesn't exist!");
		}

   	}

   	public void printUsersBySurname(String surname)
   	{
   		boolean flag = true;
	    for(int i = 0; i < allClients.size(); i++)
		{
			Client client = allClients.get(i);
			String clientSurname = client.getClientSurname();

			if(clientSurname.equalsIgnoreCase(surname))
			{
				client.printUsersInfo();
				flag = false;
			}
		}

		if(flag)
		{
			System.out.println("User doesn't exist!");
		}

   	}

   	public void printUsersByPesel(long pesel)
   	{
   		boolean flag = true;

	    for(int i = 0; i < allClients.size(); i++)
		{
			Client client = allClients.get(i);
			long clientPesel = client.getClientPesel();

			if(clientPesel == pesel)
			{
				client.printUsersInfo();
				flag = false;
			}
		}

		if(flag)
		{
			System.out.println("User doesn't exist!");
		}	
   	}


   	public void printUsersByAddress(String address)
   	{
   		boolean flag = true;

	    for(int i = 0; i < allClients.size(); i++)
		{
			Client client = allClients.get(i);
			String clientAddress = client.getClientAddress();

			if(clientAddress.equalsIgnoreCase(address))
			{
				client.printUsersInfo();
				flag = false;
			}
		}

		if(flag)
		{
			System.out.println("User doesn't exist!");
		}	
   	}


    




}