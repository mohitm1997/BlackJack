import java.util.ArrayList;

public class Hint
{
	private static int runningCount = 0;
	private static int numHighCards = 16;
	private static int numLowCards = 32;
	private static int numAces = 4;
	private static int numCardsLeft = 52;
	private static ArrayList<Card> c = new ArrayList<Card>();

	//Every time a card is dealt, it is added to an arraylist of dealt cards, additionally, the running count is adjusted based on the cards value
	public static void countCard(Card i)
	{
		c.add(i);
		if(i.getFace() == 1 || i.getValue() == 10)
		{
			runningCount -= 1;
		}
		else if(i.getValue() <= 6 && i.getValue() >= 2)
		{
			runningCount += 1;
		}

	}

	//returns the current running count
	public static int getRunningCount()
	{
		return runningCount;
	}

	//gives a hint based on the running count stating how much to bet
	public static String giveBetHint(double standardBet)
	{
		if(runningCount <= 1)
		{
			return "Hint: You should bet a minimal amount.";
		}
		else
		{
			return "Hint: You should bet $" + standardBet * (runningCount - 1) + ".";
		}
	}

	//gives a hint based on the percetnage of high cards/low cards/aces left
	public static String givePlayHint()
	{
		for(int x = 0; x < c.size(); x++)
		{
			if(c.get(x).getValue() == 11)
			{
				numAces--;
				numCardsLeft--;
			}
			else if(c.get(x).getValue() == 10)
			{
				numHighCards--;
				numCardsLeft--;
			}
			else
			{
				numLowCards--;
				numCardsLeft--;
			}


		}

		double percentHigh = ((double)numHighCards/numCardsLeft)*100.0;
		double percentLow = ((double)numLowCards/numCardsLeft)*100.0;
		double percentAce = ((double)numAces/numCardsLeft)*100.0;

		numHighCards = 16;
		numLowCards = 32;
		numAces = 4;
		numCardsLeft = 52;


		return "Hint:You have about a " + Math.round(percentHigh) + "% chance of drawing a card with value 10.\nYou have about a " + Math.round(percentLow) + "% chance of drawing a card with value less than 10.\nYou have about a " + Math.round(percentAce) + "% chance of drawing an Ace.";
	}

	//resets the various fields when the deck is shuffled
	public static void reset()
	{
		c.clear();
		numHighCards = 16;
		numLowCards = 32;
		numAces = 4;
		numCardsLeft = 52;
		runningCount = 0;
	}
}