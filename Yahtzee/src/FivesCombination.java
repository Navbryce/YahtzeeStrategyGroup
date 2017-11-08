

public class FivesCombination extends AbstractYahtzeeCombination{
	@Override
	public int score(int[] dice) {
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			if (dice[i] == 5)
				score+=5;
		}
		return score;
	}

	@Override
	public String name() {
		return "FivesCombination";
	}

	@Override
	public String description() {
		return "Adds up sum of all dice with the number 5";
	}

	@Override
	public boolean upperSection() {
		return true;
	}
}
