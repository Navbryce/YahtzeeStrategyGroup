

public class FourOfAKindCombination extends AbstractYahtzeeCombination{

	public int score(int[] dice) {
		int numDice=0;
		for(int x=1; x< 7; x++)
		{
			for(int y=0; y<dice.length;y++)
			{
				if(dice[y] == x)
				{
					numDice++;
				}
			}
			if(numDice >= 4)
			{
				int returnVal=0;
				for(int y=0; y<dice.length;y++)
				{
					returnVal+=dice[y];
				}
				return returnVal;
			}
			else
				numDice=0;
		}
		return 0;
	}

	
	public String name() {
		return "FourOfAKindCombination";
	}

	
	public String description() {
		return "Gives the sum of all dice if at least four dice have the same value";
	}

	
	public boolean upperSection() {
		return false;
	}
}
