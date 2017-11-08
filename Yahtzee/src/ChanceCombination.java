

public class ChanceCombination extends AbstractYahtzeeCombination{
	@Override
	public int score(int[] dice) {
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			score+= dice[i];
		}
		return score;
	}

	@Override
	public String name() {
		return "ChanceCombination";
	}

	@Override
	public String description() {
		return "Adds all dice";
	}

	@Override
	public boolean upperSection() {
		return false;
	}

}
