import java.util.ArrayList;

// This class represents the deck of cards from which cards are dealt to players.
public class Deck
{
	private Card[] deck;
	private int dealt = 0;
	private ArrayList<Card> dealtCards = new ArrayList<Card>();


	// This constructor builds a deck of 52 cards.
	public Deck()
	{
		deck = new Card[52];
		int count = 0;
		for(int suit = 0; suit < 4; suit++)
		{
			for(int num = 1; num <= 13; num++)
			{
				deck[count] = new Card(suit, num);
				count++;
			}
		}
	}


	// This method takes the top card off the deck and returns it.
	public Card deal()
	{
		if(isEmpty())
		{
			shuffle();
		}
		dealt++;
		dealtCards.add(deck[dealt - 1]);
		Hint.countCard(deck[dealt - 1]);
		return deck[dealt - 1];
	}

	// This method deals the card at the specified index of the deck (for testing purposes)
	public Card dealCardAt(int x)
	{
		dealt++;
		dealtCards.add(deck[dealt-1]);
		Hint.countCard(deck[x]);
		return deck[x];
	}

	// this method returns true if there are no more cards to deal, false otherwise
	public boolean isEmpty()
	{
		if(dealt == 52)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//this method puts the deck in some random order
	public void shuffle()
	{
		for(int x = 0; x < 52; x++)
		{
			int i = (int) (Math.random()*(x+1));
			Card temp = deck[x];
			deck[x] = deck[i];
			deck[i] = temp;
		}
		dealt = 0;
		Hint.reset();
	}

	//prints every card in the deck (mainly for testing purposes)
	public void printDeck()
	{
			for(int x = 0; x < 52; x++)
			{
				System.out.print(deck[x].getSuit() + " " + deck[x].getFace());
				System.out.println();
			}
	}

	//returns an arraylist of the cards that have been dealt
	public ArrayList<Card> getDealtCards()
	{
		return dealtCards;
	}


}

