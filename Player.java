//This class represents the players (including the dealer) that receive hands and play the game of blackjack while betting
import java.util.ArrayList;

public class Player
{
	//attributes of players
	private String name;
	private double balance;
	private double initBalance;
	private boolean busted = false;
	private double bet;
	private int handValue = 0;
	private boolean dealer = false;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private int numAces = 0;
	private int acesDropped = 0;
	private double insuranceBet = 0;
	private boolean didInsuranceBet = false;

	//Constructor for dealer (no blanace, sets dealer variable to true)
	public Player()
	{
		name = "Dealer";
		dealer = true;
	}

	//Constructor for normal player, sets name and starting balance
	public Player(String n, double b)
	{
		name = n;
		balance = b;
		initBalance = b;
	}

	//Sets the bet made by the player for the round
	public void setBet(double x)
	{
		bet = x;
	}

	//Makes player win by adding their bet amount to their balance
	public void win()
	{
		balance += bet;
	}

	//Makes player lose by subtracting bet amount from balance
	public void lose()
	{
		balance -= bet;
	}

	//Makes player bust, subtracts bet form balance, returns message indidcating the player busted
	public String bust()
	{
		busted = true;
		if(!dealer)
		{
			lose();
		}
		return name + " busted. \n";
	}

	//Adds the dealt card to the player's hand, adds value of the card to player's hand's value
	public void addToHand(Card x)
	{
		hand.add(x);
		handValue += x.getValue();
	}

	//Returns the bet amount
	public double getBet()
	{
		return bet;
	}

	//Returns the player's name
	public String getName()
	{
		return name;
	}

	//Returns whether or not the player has busted
	public boolean isBusted()
	{
		return busted;
	}

	//Returnes the value of the player's hand
	public int getHandValue()
	{
		return handValue;
	}

	//Returns the current balance of the player
	public double getBalance()
	{
		return balance;
	}

	//Returns the starting balance of the player
	public double getInitBalance()
	{
		return initBalance;
	}

	//Returns whether or not the player is the dealer
	public boolean isDealer()
	{
		return dealer;
	}

	//Returns a String represetnation of every card in the player's hand
	public String printHand()
	{
		String result = "";

		for(int x = 0; x < hand.size(); x++)
		{
			result += hand.get(x).toString() + " | ";
		}

		return result;
	}

	//Returns a single card at specified index of the player's hand
	public Card getCardAt(int x)
	{
		return hand.get(x);
	}

	//Reset's player's hand, etc. for new round
	public void resetHand()
	{
		hand.clear();
		handValue = 0;
		busted = false;
		numAces = 0;
		acesDropped = 0;
		insuranceBet = 0;
		didInsuranceBet = false;
	}

	//Returns whether or not the player has an ace, determines how many aces player has
	public boolean hasAce()
	{
		boolean hasAce = false;
		int counter = 0;
		for(int x = 0; x < hand.size(); x++)
		{
			if(hand.get(x).getFace() == 1)
			{
				hasAce = true;
				counter++;
			}
		}

		numAces = counter;
		return hasAce;
	}

	//Drps the value of the ace from 11 to 1 to prevent a player form busting
	public void dropAceVal()
	{
			handValue -= 10;
			numAces--;
			acesDropped++;
	}

	//Returns whether or not every ace the player has has been dropped to the lower value
	public boolean isAceDropped()
	{
		if(numAces == acesDropped)
		{
			return true;
		}

		else
		{
			return false;
		}
	}

	//Sets insurance bet to half of the original bet
	public void setInsuranceBet()
	{
		insuranceBet = bet/2;
		didInsuranceBet = true;
	}

	//Adds value of insuranceBet to balance if won
	public void winInsuranceBet()
	{
		balance += insuranceBet;
	}

	//Subtracts value of insuranceBet from balance if lost
	public void loseInsuranceBet()
	{
		balance -= insuranceBet;
	}

	//Returns value of insuranceBet
	public double getInsuranceBet()
	{
		return insuranceBet;
	}

	//Returns whether or not the player set an insurance bet
	public boolean didInsurance()
	{
		return didInsuranceBet;
	}

}