import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

class BankSystem 
{

	static final double MAX_AMOUNT = 1000000000000000.0;
	static final int MAX_CHARACTERS = 50;

	static char userChoise;
	static Scanner reader;
	static AllClients allClients;





	public static void main(String[] args)
 	{
		allClients = new AllClients();
		loadDataBase();		
		showMainMenu();
			
 		do
 		{
			getChoiseLetterFromUser();

			switch(Character.toLowerCase(userChoise))
			{
				case 'n':		
				{	
					getInfoAboutClientAndAdd();
 					showMainMenu();
					break;
				}
				case 's':
				{
					if(allClients.isEmpty())
					{
						System.out.println("\nThere are no clients yet!\n");
					}
					else
					{
						allClients.printAllClients();
					}
 					showMainMenu();
 					break;
				}
				case 'd':
				{		
					System.out.print("Please, enter an ID of user to delete: ");
					int idOfUserToDelete = getPositiveInteger();

					if(askIfUserIsSure())
					{
						if(allClients.deleteAccount(idOfUserToDelete))
						{
							System.out.println("User deleted successfully!");
						}
						else
						{
							System.out.println("User doesn't exist!");
						}	
					}
					else
					{
						System.out.println("Operation cancelled.");
					}

 					showMainMenu();
					break;

				}
				case 'm':
				{
					System.out.println("Please, enter an ID of user to receive the money: ");
					int idOfUserToDeposit = getPositiveInteger();
					double amount = getAmount();

					System.out.println("Id of user to deposit: " + idOfUserToDeposit + ". Amount: "+ amount + " USD.");
					if(askIfUserIsSure())
					{
						if(allClients.deposit(idOfUserToDeposit, amount))
						{
							System.out.println("Operation successfull!");
						}
						else
						{
							System.out.println("User doesn't exist!");
						}	
					}
					else
					{
						System.out.println("Operation cancelled.");
					}


					showMainMenu();
					break;
				}
				case 'w':
				{
					System.out.println("Please, enter an ID of user to withdraw the money: ");
					int idOfUserToWithdraw = getPositiveInteger();
					double amount = getAmount();

					System.out.println("Id of user to withdraw: " + idOfUserToWithdraw + ". Amount: "+ amount + " USD.");
					if(askIfUserIsSure())
					{
						if(allClients.withdraw(idOfUserToWithdraw, amount))
						{
							System.out.println("Operation successfull!");
						}
						else
						{
							System.out.println("User doesn't exist, or not enough money on this account.");
						}	
					}
					else
					{
						System.out.println("Operation cancelled.");
					}

					showMainMenu();
					break;
				}
				case 't':
				{
					System.out.print("Pobierz pieniadze od uzytkownika o id: ");
					int idOfSender = getPositiveInteger();
					System.out.print("Wyslij pieniadze do uzytkownika o id: ");
					int idOfReceiver = getPositiveInteger();
					System.out.print("Podaj kwote: ");
					double amount = getAmount();

					System.out.println("Id of sender: " + idOfSender + ". Id of receiver: " + idOfReceiver + ". Amount: "+ amount + " USD.");
					if(askIfUserIsSure())
					{
						if(allClients.transferMoney(idOfSender, idOfReceiver, amount))
						{
							System.out.println("Operation successfull!");
						}
						else
						{
							System.out.println("Transfer failed. User doesn't exist or not enough money on this account.");
						}	
					}
					else
					{
						System.out.println("Operation cancelled.");
					}


					showMainMenu();
					break;
				}
				case 'f':
				{
					char choise;
					reader = new Scanner(System.in);
					String input = "";
					boolean flag = false;

					do
					{
						flag = false;
						do
						{
							showCriteria();
							System.out.print("Please, enter a single letter: ");
							input = reader.nextLine().toString();
						}
						while(input.length() != 1);

						choise = input.charAt(0);

						switch(choise)
						{
							case 'i':
							{
								System.out.println("Please enter an ID");
								int id = getPositiveInteger();
								allClients.printUserById(id);
								break;
							}
							case 'n':
							{
								System.out.println("Please enter a name");
								String name = getSingleWord();
								allClients.printUsersByName(name);
								//System.out.println("przeszlo");
								break;
							}
							case 's':
							{
								System.out.println("Please enter a surname");
								String surname = getSingleWord();
								allClients.printUsersBySurname(surname);
								break;
							}
							case 'p':
							{
								System.out.println("Please enter a pesel");
								long pesel = getPesel();
								allClients.printUsersByPesel(pesel);
								break;
							}
							case 'a':
							{
								System.out.println("Please enter an address");
								String address = getString();
								allClients.printUsersByAddress(address);
								break;
							}
							default:
							{
								flag = true;
								break;
							}
						}

					}
					while(flag);

					showMainMenu();
					break;

				}


				default:
				{
					System.out.println("Please, enter a correct letter: ");
				}
				case 'q':
				{
					continue;
				}
			}
		
 		}
 		while(userChoise != 'q');
		

		saveAllClients();
		reader.close();
	}	



	public static void showMainMenu()
	{
		System.out.println("\nBank system demo\n" +
			"Menu:\n" + 
			"n - add a new client\n" +
			"s - show all clients\n" +
			"q - quit\n" +
			"d - delete account\n" +
			"m - make a payment into account\n" +
			"w - withdraw money from account\n" +
			"t - transfer money\n" + 
			"f - find clients\n" + 
			"\nPlease input a single letter and press Enter:  "
		 );
	}



	public static void getInfoAboutClientAndAdd()
	{
		String name, surname, address;
		long pesel;
		int id;
		System.out.print("Please enter a name of a new client: ");
		name = getSingleWord();
		System.out.print("Please enter a surname: ");
		surname = getSingleWord();
		System.out.print("Please enter a pesel: ");
		pesel = getPesel();
		System.out.print("Please enter an address: ");
		address = getString();
		id = allClients.getLastId() + 1;

		 Client newClient = new Client(name, surname, pesel, address, id);
		allClients.addClient(newClient);
	}



	public static void getChoiseLetterFromUser()
	{
		reader = new Scanner(System.in);
		String input = "";


		input = reader.nextLine().toString();

		if(input.length() != 1)
		{
			System.out.println("Please, enter a correct letter: ");
			getChoiseLetterFromUser();
		}
		else
		{
			userChoise = input.charAt(0);
		}	

	}


	private static String getSingleWord()		
	{	
		String word;
		Matcher matcher;
		Pattern compiledPattern = Pattern.compile("[^a-zA-Z_]");
		word = reader.nextLine().toString();
		matcher = compiledPattern.matcher(word);

		while(word.length() > MAX_CHARACTERS || word.equals("") || matcher.find())
		{
			System.out.print("Max Lenth is " + Integer.toString(MAX_CHARACTERS) + ".\n" +  "You can't enter numbers, special characters and whitespaces.\nTry again: ");
			word = reader.nextLine().toString();
			matcher = compiledPattern.matcher(word);
		}	

		return word;
	}

	private static double getAmount()		
	{	
		String word;
		double amount = -1;
		while(amount <= 0)
		{
			try
			{
				System.out.print("Enter an amount: ");
				word = reader.nextLine().toString();
				amount = Double.parseDouble(word);
				if(amount <= 0 || amount > MAX_AMOUNT)
				{
					System.out.println("zla kwota");
					amount = -1;
				}
			}
			catch(NumberFormatException e)
			{
				System.out.println("zla kwota");
			}
		}	

		return amount;
	}

	private static long getPesel()
	{
		String word;
		Matcher matcher;
		Pattern compiledPattern = Pattern.compile("[^0-9]");
		word = reader.nextLine().toString();
		matcher = compiledPattern.matcher(word);

		while(word.equals("") /*|| word.length() != 11*/ || matcher.find())
		{
			System.out.print("Pesel should be a 11-digits number. Try again: ");
			word = reader.nextLine().toString();
			matcher = compiledPattern.matcher(word);
		}

		return Long.parseLong(word);	
	}


	private static String getString()
	{
		String word = reader.nextLine().toString();
		while(word.equals(""))
		{
			System.out.print("This field is required. Try again: ");
			word = reader.nextLine().toString();
		}

		return word;
	} 

	private static int getPositiveInteger()	
	{
		Pattern compiledPattern = Pattern.compile("[^0-9]");
		String word = reader.nextLine().toString();
		Matcher matcher = compiledPattern.matcher(word);


		while(word.equals("") || word.equals("0") || matcher.find())
		{
			System.out.print("It must be a positive number: ");
			word = reader.nextLine().toString();
			matcher = compiledPattern.matcher(word);
		}


		return Integer.parseInt(word);	
	}



	private static void showCriteria()
	{
		System.out.println("\n " + 
		"Choose criterion:\n" + 
		"i - id\n" +
		"n - name\n" +
		"s - surname\n" +
		"p - pesel\n" +
		"a - address\n" +
		"\nPlease input a single letter and press Enter:  "
	 );	
	}


	private static void saveAllClients()
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream("data.bin");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(allClients);
			objectOutputStream.close();
			fileOutputStream.close();	
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(); 
		}
	}


	private static void loadDataBase()	
	{
		try 	
		{
			FileInputStream fis = new FileInputStream("data.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			allClients = (AllClients) ois.readObject();
			ois.close();
			fis.close();
			System.out.println("Database loaded succesfully!");
		}
		catch(IOException ioe)   
		{
			System.out.println("Can't load database!");
		}
		catch(ClassNotFoundException c)
		{
			System.out.println("Class not found");
		}

	}



	 static private boolean askIfUserIsSure()
    {
		Scanner reader = new Scanner(System.in);
    	System.out.println("Do you really want to do this operation? [y/n]");
    	String word = reader.nextLine().toString();
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



}