

public class LargeStraightCombination extends AbstractYahtzeeCombination{

	
	public int score(int[] dice) {
		String bogo = "";
		for(int x=0; x<dice.length;x++)
		{
			if(!bogo.contains(""+dice[x]))
				bogo += dice[x];
		}
		if(bogo.contains("12345") || bogo.contains("23456"))
			return 40;
		return 0;
	}

	
	public String name() {
		return "LargeStraightCombination";
	}

	
	public String description() {
		return "Gives 30 points if there are 5 consecutive dice, zero points otherwise";
	}

	
	public boolean upperSection() {
		return false;
	}

}
