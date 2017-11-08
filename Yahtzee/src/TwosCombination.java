

public class TwosCombination extends AbstractYahtzeeCombination {

	@Override
	public int score(int[] dice) {
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			if (dice[i] == 2)
				score+=2;
		}
		return score;
	}

	@Override
	public String name() {
		return "TwosCombination";
	}

	@Override
	public String description() {
		return "Adds up sum of all dice with the number 2";
	}

	@Override
	public boolean upperSection() {
		return true;
	}

}
