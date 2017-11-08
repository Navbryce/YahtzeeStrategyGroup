

public class BasicStrategy extends YahtzeeComputerStrategy{

	public void reroll(int[] dice, int rollNumber, PlayerRecord record,
			boolean[] reroll) {
		if(AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("YahtzeeCombination")].score(dice)==0)
		{
			for(int x=0; x<reroll.length;x++)
			{
				reroll[x]=true;
			}
		}
		
	}

	public int chooseCombination(int[] dice, PlayerRecord record) {
		AbstractYahtzeeCombination[] availableCombinations;
		availableCombinations = record.availableCombinations();

		int maxScore=0;
		int indexOfMaxScore=-1;
		for(int x=0; x<availableCombinations.length;x++)
		{
			int score = availableCombinations[x].score(dice);
			if(score > maxScore)
			{
				maxScore = score;
				indexOfMaxScore = x;
			}
		}
		if(maxScore == 0)
		{
			if(availableCombinations[availableCombinations.length - 1].upperSection())
			{
				return 0;
			}
			else
			{
				return availableCombinations.length - 1;
			}
		}
		else
		{
			return indexOfMaxScore;
		}
	}

	public String author() {
		return "Yarhartzee the Pirate";
	}

	public String playerName() {
		return "Ol' Cap'n Wood-legs";
	}

	public void finalResults(int[] dice, PlayerRecord record) {
		
	}

}
