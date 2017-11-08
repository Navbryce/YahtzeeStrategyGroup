

public class SixesCombination extends AbstractYahtzeeCombination {
	@Override
	public int score(int[] dice) {
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			if (dice[i] == 6)
				score+=6;
		}
		return score;
	}

	@Override
	public String name() {
		return "SixesCombination";
	}

	@Override
	public String description() {
		return "Adds up sum of all dice with the number 6";
	}

	@Override
	public boolean upperSection() {
		return true;
	}
}
