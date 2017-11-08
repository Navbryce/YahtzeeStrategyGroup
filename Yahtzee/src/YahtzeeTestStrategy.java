public class YahtzeeTestStrategy extends AbstractComputerYahtzeePlayer{
	private AbstractYahtzeeCombination[] availableCombinations;
	public String playerName(){
		return "CaptianSquigglySquid";
	}
	public void reroll(int[] dice, int rollNumber, PlayerRecord record,boolean[] reroll){
		//Always rerolls unless you have a yahtzee
		if((AbstractYahtzeeCombination.allCombinations()[AbstractYahtzeeCombination.combinationIndex("YahtzeeCombination")]).score(dice) != 50){
			for(int x=0; x<reroll.length;x++){
				reroll[x] = true;
			}
		}
	}
	public int chooseCombination(int[] dice, PlayerRecord record) {
		availableCombinations = record.availableCombinations();
		int maxScore=0;int indexOfMaxScore=-1;
		for(int x=0; x<availableCombinations.length;x++){
			int score = availableCombinations[x].score(dice);
			if(score > maxScore){
				maxScore = score;indexOfMaxScore = x;
			}
		}
		if(maxScore == 0){
			if(availableCombinations[availableCombinations.length - 1].upperSection()){
				return 0;
			}else{
				return availableCombinations.length - 1;
			}	
		}else{
			return indexOfMaxScore;
		}
	}
	public void finalResults(int[] dice, PlayerRecord record) {
		
	}
	public String author() {
		return "Yahrtzee the pirate";
	}
}