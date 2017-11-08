

public class ThreesCombination extends AbstractYahtzeeCombination{

	@Override
	public int score(int[] dice) {
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			if (dice[i] == 3)
				score+=3;
		}
		return score;
	}

	@Override
	public String name() {
		return "ThreesCombination";
	}

	@Override
	public String description() {
		return "Adds up sum of all dice with the number 3";
	}

	@Override
	public boolean upperSection() {
		return true;
	}

}
