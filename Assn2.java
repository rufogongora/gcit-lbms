import java.util.Scanner;


public class Assn2 {

	private static class PileOfChips{


		private int noOfChips;
		private Player player1;
		private Player player2;

		//Player class just to keep everything organized
		private class Player{
			public String name;
			public int noOFChips;

			public Player()
			{
				name = "";
			}
		}

		//Constructor, initialize empty players
		public PileOfChips()
		{
			player1 = new Player();
			player2 = new Player();
		}

		//Return the total amount of chips
		public int getNumberOfChips()
		{
			return noOfChips;
		}

		//Get the total number of chips for X player
		public int getNumberOfChipsForPlayer(int n)
		{
			if (n == 1)
				return player1.noOFChips;
			return player2.noOFChips;
		}

		//Initialize the number of chips if valid
		public boolean setNumberOfChips(int n)
		{
			//if it's an even number, then return false
			if (n%2 == 0)
				return false;

			if (n < 3)
				return false;

			//if it's an odd number then set it, and return true
			noOfChips = n;
			return true;
		}

		//Returns X player name
		public String getPlayerName(int n)
		{
			if (n == 1)
				return player1.name;
			return player2.name;
		}

		//gets the number of takeable chips 
		public int takeableChips()
		{
			if (noOfChips <= 2)
				return 1;
			if (noOfChips%2 != 0)
				return ((noOfChips - 1) /2);
			return (noOfChips / 2);
			
		}

		//Perform the take chips action
		public void takeChips(int n, int turn)
		{
			if (turn == 1)
				player1.noOFChips += n;
			else
				player2.noOFChips += n;

			noOfChips -= n;
		}

		//Set a name to X player
		public boolean setName(String name, int playerNo)
		{

			//if the name is empty, then this is invalid
			if (name.isEmpty())
				return false;

			
			//switch to set the name depending on which player you're assigning to
			switch (playerNo) {
			case 1:
				player1.name = name;
				break;
			case 2:
				if (name.equalsIgnoreCase(player1.name))
					return false;
				player2.name = name;
				break;
			default:
				return false;
			}

			//If there are no errors then return true
			return true;
		}

		//Return the winner depending on the conditions
		public String getWinner()
		{
			if (player1.noOFChips % 2 != 0)
				return player1.name;
			return player2.name;
		}


	}



	public static void main(String[] args) {

		//MAIN OUTER LOOP, REAPEATABLE 
		while (true)
		{
			//initilize the pile of chips object
			PileOfChips pileOfChips = new PileOfChips();

			//Initialize the scanner and other variables
			Scanner sc = new Scanner(System.in);
			boolean finished = false;

			//read the players names
			for (int i = 1; i <= 2; i++)
			{
				//A switch to display the proper message
				switch (i) {
				case 1:
					System.out.println("What is the name of the first player? ");
					break;
				case 2:
					System.out.println("What is the name of the second player?");
					break;
				default:
					break;
				}
				
				//read the next name
				String name = sc.nextLine();
				if (!pileOfChips.setName(name, i))
				{
					switch (i) {
					case 1:
						System.out.println("Name cannot be empty");
						break;
					case 2:
						if (name.isEmpty())
							System.out.println("Name cannot be empty");
						else
							System.out.println("Both players can't be named " + name);
						break;
					default:
						break;
					}
					i--;
				}
			}

			boolean ready = false;
			//validate the number of chips loop
			do {
				
				int n;
				System.out.println("How many chips does the initial pile contain?");

				try {
					n = sc.nextInt();
					if (n < 3)
					{

						System.out.println("You have to start with at least 3 chips. Choose another number:");
						continue;
					}
					//Try to initialize the number of chips
					ready = pileOfChips.setNumberOfChips(n); 
					if (!ready)
						//If the return was false then it means there was an even number of chips for initialization
						System.out.println("You have to start with an odd number of chips");
				} catch (Exception e) {
					//if n is not a number, then catch the error
					System.out.println("That's not a number");
					//clean the scanner
					sc.next();
					continue;
				}

			} while (!ready);

			//initialize the turn engine on turn 1
			int turnPlayer = 1;
			//MAIN GAME LOOP
			while (!finished)
			{
				System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");

				//display the number of chips for every player
				System.out.println(pileOfChips.getPlayerName(1) + " has " + pileOfChips.getNumberOfChipsForPlayer(1) + " chips.");
				System.out.println(pileOfChips.getPlayerName(2) + " has " + pileOfChips.getNumberOfChipsForPlayer(2) + " chips.");

				//display whose turn is
				System.out.println("It is your turn, " + pileOfChips.getPlayerName(turnPlayer));
			
				//Display the remaining number of chips in the pile
				if (pileOfChips.getNumberOfChips() != 1)
					System.out.println("There are " + pileOfChips.getNumberOfChips() + " chips remaining");
				else
					System.out.println("There is 1 chips remaining");
				
				
				System.out.println("You make take any number of chips from 1 to " +  pileOfChips.takeableChips());

				//The logic of the turn, it will keep trying until the user inputs a correct value
				while (pileOfChips.getNumberOfChips() != 0)
				{
					System.out.println("How many will you take "	+ pileOfChips.getPlayerName(turnPlayer));
					try {
						int n = sc.nextInt();
						if (n > pileOfChips.takeableChips())
						{
							System.out.print("Illegal move: you may not take more than "+  pileOfChips.takeableChips() +  " chips. ");
							continue;
						}
						if (n <= 0)
						{
							System.out.println("Illegal move: you must take at least one chip. ");
							continue;
						}
						pileOfChips.takeChips(n, turnPlayer);
						break;
					}
					catch (Exception e)
					{
						System.out.print("That's not a number, ");
						sc.next();
					}

				} 

				//this is to swap turns
				if (turnPlayer == 1)
					turnPlayer = 2;
				else
					turnPlayer = 1;

				//this is game over
				if (pileOfChips.getNumberOfChips() == 0)
					finished = true;			

			}
			// finish the game
			System.out.println(pileOfChips.getWinner() + " wins!");
			System.out.print("Play another game? (y/n) ");
			String opt = sc.next();
			if (!opt.equalsIgnoreCase("y"))
				break;
			sc.close();
		}
		
	}

}
