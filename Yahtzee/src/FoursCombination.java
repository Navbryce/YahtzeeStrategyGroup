

public class FoursCombination extends AbstractYahtzeeCombination{

	@Override
	public int score(int[] dice) {
		int score = 0;
		for(int i = 0; i < dice.length; i++){
			if (dice[i] == 4)
				score+=4;
		}
		return score;
	}

	@Override
	public String name() {
		return "FoursCombination";
	}

	@Override
	public String description() {
		return "Adds up sum of all dice with the number 4";
	}

	@Override
	public boolean upperSection() {
		return true;
	}

}
