//Main loop of the black jack game; rules, betting, etc. are handled here
import java.util.ArrayList;

public class BlackJack
{
	public static void main(String[] args)
	{
		//ArrayList for the players playing and the dealer
		ArrayList<Player> players = new ArrayList<Player>();

		//Variable declarations
		boolean again = true;
		boolean allBusted = true;

		//arbitrary string for pause
		String pause;

		//Creates a new Deck of 52 cards
		Deck deck = new Deck();
		//Shuffles deck into a random order
		deck.shuffle();

		System.out.println("-----------");
		System.out.println("|Blackjack|");
		System.out.println("-----------");
		System.out.println("Press Enter to start!");
		pause = IO.readString();

		//Determines number of players and makes objects with name and balance for that amount of players
		System.out.println("How many players are there?");
		int numPlayers = IO.readInt();

		while(numPlayers > 6 || numPlayers <= 0)
		{
			System.out.println("Invalid number of players (must have at least 1 and at most 6).");
			numPlayers = IO.readInt();
		}

		for(int x = 1; x <= numPlayers; x++)
		{
			System.out.println("Player " + x + ": What is your name?");
			String n = IO.readString();
			System.out.println("Player " + x + ": Enter your balance.");
			double b = IO.readDouble();
			while(b <= 0)
			{
				System.out.println("Balance must be atleast 0");
				b = IO.readDouble();
			}
			players.add(new Player(n,b));
		}

		//creates dealer
		players.add(new Player());
		int dealer = players.size()-1;

		//Runs the main portions of the game, loops back if again = true
		do
		{
			//Sets bet amounts for each players
			for(int x = 0; x < numPlayers; x++)
			{
				//Asks player if he/she wants a hint on how much to bet based on card counting
				System.out.println(players.get(x).getName() + ", do you want a hint on your bet?");
				String choice = IO.readString();
				while(!choice.equalsIgnoreCase("yes")&&!choice.equalsIgnoreCase("no"))
				{
					System.out.println("Please enter either 'yes' or 'no'.");
					choice = IO.readString();
				}

				if(choice.equalsIgnoreCase("yes"))
				{
					System.out.println("What is you standard betting unit?");
					int betUnit = IO.readInt();

					System.out.println(Hint.giveBetHint(betUnit));
				}

				System.out.println(players.get(x).getName() + ", place your bet.");
				int bet = IO.readInt();
				while(bet > players.get(x).getBalance())
				{
					System.out.println("Bet cannot be greater than balance.");
					bet = IO.readInt();
				}
				players.get(x).setBet(bet);
			}

			//Deals the initial two cards to each player, prints them as well as the value of the hand
			for(int x = 0; x < numPlayers; x++)
			{
				players.get(x).addToHand(deck.deal());
				players.get(x).addToHand(deck.deal());

				System.out.println(players.get(x).getName() + ", your hand is: " + players.get(x).printHand());
				System.out.println("The value of your hand is: " + players.get(x).getHandValue() + ".");

				//If two aces are dealt, the value is 22, so one of the aces is dropped to its low value (1)
				if(players.get(x).getHandValue() > 21 && players.get(x).hasAce() && !(players.get(x).isAceDropped()))
				{
					players.get(x).dropAceVal();
					System.out.println();
					System.out.println("Ace value dropped from 11 to 1 to prevent busting.");
					System.out.println("The value of your hand is now: " + players.get(x).getHandValue() + ".");
				}

				//Pause
				System.out.println("Press Enter to continue...");
				pause = IO.readString();

			}

			//Deals dealer's two cards but only displays the second one (face up)
			players.get(dealer).addToHand(deck.deal());
			players.get(dealer).addToHand(deck.deal());
			System.out.println("The dealer's face up card is: " + players.get(dealer).getCardAt(1));

			//If the dealer is dealt an ace, each player is asked whether or not they want to set an insurance bet
			if(players.get(dealer).getCardAt(1).getValue() == 11)
			{
				for(int x = 0; x < numPlayers; x++)
				{
					System.out.println(players.get(x).getName() + ", would you like to set an insurance bet?");
					String choice = IO.readString();

					while(!(choice.equalsIgnoreCase("yes")) && !(choice.equalsIgnoreCase("no")))
					{
						System.out.println("Please enter either 'yes' or 'no'.");
						choice = IO.readString();
					}

					if(choice.equalsIgnoreCase("yes"))
					{
						players.get(x).setInsuranceBet();
					}
				}
			}


			//Loop for hit/stand process that goes through every player
			for(int x = 0; x < numPlayers; x++)
			{
				if(players.get(x).getBet() * 2 <= players.get(x).getBalance())
				{
					System.out.println(players.get(x).getName() + ", do you want to double down?");
					String choice = IO.readString();

					while(!(choice.equalsIgnoreCase("yes")) && !(choice.equalsIgnoreCase("no")))
					{
						System.out.println("Please enter either 'yes' or 'no'.");
						choice = IO.readString();
					}

					if(choice.equalsIgnoreCase("yes"))
					{
						players.get(x).setBet(players.get(x).getBet() * 2);
					}

				}

				//Asks eveyr player if they want to hit or stand
				String option;
				do
				{
					//Asks player if they want a hint regarding the probability of drawing certain cards
					System.out.println("\n" + players.get(x).getName() + ", would you like a hint?");
					String choice = IO.readString();
					while(!choice.equalsIgnoreCase("yes")&&!choice.equalsIgnoreCase("no"))
					{
						System.out.println("Please enter either 'yes' or 'no'.");
						choice = IO.readString();
					}

					if(choice.equalsIgnoreCase("yes"))
					{
						System.out.println(Hint.givePlayHint());
					}

					System.out.println("\n" + players.get(x).getName() + ", would you like to hit or stand?");
					option = IO.readString();
					while(!(option.equalsIgnoreCase("hit"))&&!(option.equalsIgnoreCase("stand")))
					{
						System.out.println("Please input either 'hit' or 'stand'.");
						option = IO.readString();
					}

					//Deals a card if player 'hits', prints hand out and the value of hand
					if(option.equalsIgnoreCase("hit"))
					{
						players.get(x).addToHand(deck.deal());
						System.out.println("Your hand is now: " + players.get(x).printHand());
						System.out.println("The value of your hand is: " + players.get(x).getHandValue() + ".");

						//Drops Ace from 11 to 1 if player has an ace that has not been dropped and the player's hand's value is above 21
						if(players.get(x).getHandValue() > 21 && players.get(x).hasAce() && !(players.get(x).isAceDropped()))
						{
							players.get(x).dropAceVal();
							System.out.println();
							System.out.println("Ace value dropped from 11 to 1 to prevent busting.");
							System.out.println("The value of your hand is now: " + players.get(x).getHandValue() + ".");
						}

						//If players hand's value is greater than 21, player busts
						else if(players.get(x).getHandValue() > 21)
						{
							System.out.println(players.get(x).bust());
							option = "stand";
						}
					}
				}while(option.equalsIgnoreCase("hit"));
			}

			//Checks if all players are busted
			for(int x = 0; x < numPlayers; x++)
			{
				if(!(players.get(x).isBusted()))
				{
					allBusted = false;
				}
			}

			//Flips over dealer's face down card, prints it, prints the hand, and the hand's value
			System.out.println("The dealer's face down card was: " + players.get(dealer).getCardAt(0));
			System.out.println("The dealer's hand is: " + players.get(dealer).printHand());
			System.out.println("The value of the dealer's hand is: " + players.get(dealer).getHandValue());

			if(players.get(dealer).getHandValue() == 21)
			{
				for(int x = 0; x < numPlayers; x++)
				{
					if(players.get(x).didInsurance())
					{
						players.get(x).winInsuranceBet();
						System.out.println("\n" + players.get(x).getName() + ", you won your insurance bet of: $" + players.get(x).getInsuranceBet());
					}
				}
			}

			else
			{
				for(int x = 0; x < numPlayers; x++)
				{
					if(players.get(x).didInsurance())
					{
						players.get(x).loseInsuranceBet();
						System.out.println("\n" + players.get(x).getName() + ", you lost your insurance bet of: $" + players.get(x).getInsuranceBet());
					}
				}
			}

			//Drops ace from 11 to 1 if dealer has ace that has not been dropped and the hand value is greater than 21
			if(players.get(dealer).getHandValue() > 21 && players.get(dealer).hasAce() && !(players.get(dealer).isAceDropped()))
			{
				players.get(dealer).dropAceVal();
				System.out.println();
				System.out.println("Ace value dropped from 11 to 1 to prevent busting.");
				System.out.println("The value of the dealer's hand is now: " + players.get(dealer).getHandValue() + ".");
			}

			System.out.println("Press Enter to continue...");
			pause = IO.readString();

			//If all players have not busted, the dealer hits until busts or his hand value is 17 or above (will hit on soft 17s)
			while(((players.get(dealer).getHandValue() < 17) || (players.get(dealer).getHandValue() == 17 && players.get(dealer).hasAce() && !(players.get(dealer).isAceDropped())))&& !allBusted)
			{
				System.out.println("The dealer hits");
				players.get(dealer).addToHand(deck.deal());
				System.out.println("The dealer's hand is now: " + players.get(dealer).printHand());
				System.out.println("The value of the dealer's hand is: " + players.get(dealer).getHandValue());

				//Drops ace if all aces have not been dropped and hand value > 21
				if(players.get(dealer).getHandValue() > 21 && players.get(dealer).hasAce() && !(players.get(dealer).isAceDropped()))
				{
					players.get(dealer).dropAceVal();
					System.out.println();
					System.out.println("Ace value dropped from 11 to 1 to prevent busting.");
					System.out.println("The value of the dealer's hand is now: " + players.get(dealer).getHandValue() + ".");
				}

				//Dealer busts if hand value > 21
				else if(players.get(dealer).getHandValue() > 21)
				{
					System.out.println(players.get(dealer).bust());
				}

				System.out.println("Press Enter to continue...");
				pause = IO.readString();
			}

			//Determines which players won/lost, the amount they won/lost, and their new balance
			for(int x = 0; x < numPlayers; x++)
			{
				if(players.get(dealer).isBusted())
				{
					if(!players.get(x).isBusted())
					{
						players.get(x).win();
						System.out.println(players.get(x).getName() + ", you won $" + players.get(x).getBet() + ". Your balance is now: $" + players.get(x).getBalance());

						System.out.println("Press Enter to continue...");
						pause = IO.readString();
					}
					else
					{
						System.out.println(players.get(x).getName() + ", you busted and lost $" + players.get(x).getBet() + ". Your balance is now: $" + players.get(x).getBalance());

						System.out.println("Press Enter to continue...");
						pause = IO.readString();
					}
				}

				else if(players.get(x).isBusted())
				{
					System.out.println(players.get(x).getName() + ", you busted and lost $" + players.get(x).getBet() + ". Your balance is now: $" + players.get(x).getBalance());

					System.out.println("Press Enter to continue...");
					pause = IO.readString();
				}

				else if(players.get(x).getHandValue() > players.get(dealer).getHandValue())
				{
					players.get(x).win();
					System.out.println(players.get(x).getName() + ", you won $" + players.get(x).getBet() + ". Your balance is now: $" + players.get(x).getBalance());

					System.out.println("Press Enter to continue...");
					pause = IO.readString();
				}

				else if(players.get(x).getHandValue() == players.get(dealer).getHandValue())
				{
					System.out.println(players.get(x).getName() + ", you tied the dealer. You did not win or lose any money. Your balance is still: $" + players.get(x).getBalance());

					System.out.println("Press Enter to continue...");
					pause = IO.readString();
				}

				else
				{
					players.get(x).lose();
					System.out.println(players.get(x).getName() + ", you lost $" + players.get(x).getBet() + ". Your balance is now: $" + players.get(x).getBalance());

					System.out.println("Press Enter to continue...");
					pause = IO.readString();
				}
			}

			//Checks if any player has $0 balance, if so, again is set to fault and the game ends
			for(int x = 0; x < numPlayers; x++)
			{
				if(players.get(x).getBalance() == 0)
				{
					again = false;
				}
			}

			//Otherwise asks all players if the want to play again, will only go again if all say yes
			for(int x = 0; x < numPlayers && again; x++)
			{
				System.out.println(players.get(x).getName() + ", would you like to play again?");
				String pAgain = IO.readString();
				while(!(pAgain.equalsIgnoreCase("yes")) && !(pAgain.equalsIgnoreCase("no")))
				{
					System.out.println("Please input either 'yes' or 'no'.");
					pAgain = IO.readString();
				}
				if(pAgain.equalsIgnoreCase("no"))
				{
					again = false;
				}

				//Resets hands and other neccessary variable for each player and dealer for next round
				players.get(x).resetHand();
				players.get(dealer).resetHand();
			}
		}while(again);

		//Prints message, and results (overall gains/losses) for each player
		System.out.println("\nThanks for playing!");
		System.out.println("----------------------");
		System.out.println("|Results of the game:|");
		System.out.println("----------------------");

		for(int x = 0; x < numPlayers; x++)
		{
			if(players.get(x).getBalance() > players.get(x).getInitBalance())
			{
				System.out.println(players.get(x).getName() + ": Balance is $" + players.get(x).getBalance() + ", gain of $" + (players.get(x).getBalance() - players.get(x).getInitBalance()));
			}

			else if(players.get(x).getBalance() < players.get(x).getInitBalance())
			{
				System.out.println(players.get(x).getName() + ": Balance is $" + players.get(x).getBalance() + ", loss of $" + (players.get(x).getInitBalance() - players.get(x).getBalance()));
			}

			else
			{
				System.out.println(players.get(x).getName() + ": Balance is $" + players.get(x).getBalance() + ", broke even");
			}
		}
	}
}