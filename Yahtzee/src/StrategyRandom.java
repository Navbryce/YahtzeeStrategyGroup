
public class StrategyRandom extends YahtzeeComputerStrategy{

	public String playerName() {
		return "Random";
	}

	@Override
	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
		for(int x=0; x<reroll.length;x++)
		{
			int roll = (int)Math.random()*2+1;
			if(roll==1)
				reroll[x]= true;
			else
				reroll[x]= false;
		}
		for(int i=0;i<record.combinationScores().length;i++){
			System.out.print(record.combinationScores()[i]+" ");
		}
		System.out.println();
	}

	@Override
	public int chooseCombination(int[] dice, PlayerRecord record) {
		return (int) (Math.random()*13+1);
	}


	public void finalResults(int[] dice, PlayerRecord record) {
	}

	public String author() {
		return "Peter";
	}
	
}
