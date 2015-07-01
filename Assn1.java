import java.util.Random;
import java.util.Scanner;


public class Assn1 {

	public static void main(String[] args) {

		/* PARAMETERS     */

		//the number of chances to guess the number
		int numberOfChances = 5; 

		//If there's a bound on generating the number, put true, then specify the maximum number bound
		boolean bound = true;
		int boundNumber = 100;

		//enable hints
		boolean hints = true;
		/* END PARAMETERS */



		//generate a random number
		Random rnd = new Random();
		int generatedNumber;
		if (!bound)
			generatedNumber = rnd.nextInt();
		else
			generatedNumber = rnd.nextInt(boundNumber);

		//Ask the user to guess it		
		System.out.println("Guess the number, you have 5 chances");

		Scanner sc = new Scanner(System.in);
		//main game loop
		for (int i = numberOfChances - 1; i >= 0; i--) {
			try {
				//read the input and check whether it's an int
				int x = sc.nextInt();

				if (x < 0)
				{
					System.out.println("No negative numbers");
					continue;
				}
				//check if the number is in between that range
				if (generatedNumber >= x - 10 && generatedNumber <= x + 10)
				{
					// FOUND IT!
					System.out.println("Got it!, the actual number was: " + generatedNumber);
					break;

				}
				else
				{
					//wrong answer
					if (i != 0)
					{
						System.out.println("Wrong answer, try again, you have: " + i + " chances");
						//if hints are enabled, then give them to the user
						if (hints)
						{
							if (generatedNumber < x)
								System.out.println("You went over the number");
							else
								System.out.println("You are below the number");
						}
					}
					else
					//if out of of chances, then game over
						System.out.println("Game Over, the actual number was: " + generatedNumber + " !");
				}

			} catch (Exception e) {
				//if it's not an int then 
				i++;
				System.out.println("That's not an integer, try again, you still have: " + i + " chances");

			}

		}



	}

}
