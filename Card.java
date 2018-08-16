// This class represents one playing card.
public class Card
{
	// Card suits (provided for your convenience - use is optional)
	public static final int SPADES   = 0;
	public static final int HEARTS   = 1;
	public static final int CLUBS    = 2;
	public static final int DIAMONDS = 3;

	// Card faces (provided for your convenience - use is optional)
	public static final int ACE      = 1;
	public static final int TWO      = 2;
	public static final int THREE    = 3;
	public static final int FOUR     = 4;
	public static final int FIVE     = 5;
	public static final int SIX      = 6;
	public static final int SEVEN    = 7;
	public static final int EIGHT    = 8;
	public static final int NINE     = 9;
	public static final int TEN      = 10;
	public static final int JACK     = 11;
	public static final int QUEEN    = 12;
	public static final int KING     = 13;


	// define fields here
	private int suit;
	private int face;

	// This constructor builds a card with the given suit and face, turned face down.
	public Card(int cardSuit, int cardFace)
	{
		suit = cardSuit;
		face = cardFace;
	}

	// This method retrieves the suit (spades, hearts, etc.) of this card.
	public int getSuit()
	{
		return suit;
	}

	// This method retrieves the face (ace through king) of this card.
	public int getFace()
	{
		return face;
	}

	// This method retrieves the numerical value of this card
	// (usually same as card face, except 1 for ace and 10 for jack/queen/king)
	public int getValue()
	{
		if(face == JACK || face == QUEEN || face == KING)
		{
			return 10;
		}
		else if(face == ACE)
		{
			return 11;
		}
		else
		{
			return getFace();
		}
	}

	//returns a String representation of the card object (acutal suits & faces)
	public String toString()
	{
		String result = "";

		switch(face)
		{
			case 1: result += "A of ";
				break;
			case 2: result += "2 of ";
				break;
			case 3: result += "3 of ";
				break;
			case 4: result += "4 of ";
				break;
			case 5: result += "5 of ";
				break;
			case 6: result += "6 of ";
				break;
			case 7: result += "7 of ";
				break;
			case 8: result += "8 of ";
				break;
			case 9: result += "9 of ";
				break;
			case 10: result += "10 of ";
				break;
			case 11: result += "J of ";
				break;
			case 12: result += "Q of ";
				break;
			case 13: result += "K of ";
				break;
		}

		switch(suit)
		{
			case 0: result += "Spades";
				break;
			case 1: result += "Hearts";
				break;
			case 2: result += "Clubs";
				break;
			case 3: result += "Diamonds";
				break;
		}

		return result;
	}

}
