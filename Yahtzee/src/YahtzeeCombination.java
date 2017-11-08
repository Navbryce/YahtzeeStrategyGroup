

public class YahtzeeCombination extends AbstractYahtzeeCombination{
	@Override
	public int score(int[] dice) {
		for(int i = 1; i < dice.length; i++){
			if(dice[i] != dice[0])
				return 0;
		}
		return 50;
	}

	@Override
	public String name() {
		return "YahtzeeCombination";
	}

	@Override
	public String description() {
		return "All five dice are the same";
	}

	@Override
	public boolean upperSection() {
		return false;
	}

}
