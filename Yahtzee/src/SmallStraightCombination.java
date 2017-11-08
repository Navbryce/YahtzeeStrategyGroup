

public class SmallStraightCombination extends AbstractYahtzeeCombination{

	
	public int score(int[] dice) {
		String bogo = "";
		for(int x=0; x<dice.length;x++)
		{
			if(!bogo.contains(""+dice[x]))
				bogo += dice[x];
		}
		if(bogo.contains("1234") || bogo.contains("2345") || bogo.contains("3456"))
			return 30;
		return 0;
	}

	
	public String name() {
		return "SmallStraightCombination";
	}

	
	public String description() {
		return "Returns 30 if you have 4 sequential dice";
	}

	
	public boolean upperSection() {
		return false;
	}

}
