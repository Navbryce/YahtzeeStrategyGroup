

public class AcesCombination extends AbstractYahtzeeCombination {
	
	public int score(int[] dice){
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			if (dice[i] == 1)
				score++;
		}
		return score;
	}
	public String name(){
		return "AcesCombination";
	}
	public String description(){
		return "Adds up sum of all dice with the number 1";
	}
	public boolean upperSection(){
		return true;
	}
	
}
