

public abstract class AbstractComputerYahtzeePlayer extends Strategy implements YahtzeePlayer{
	
	public abstract String author();
	
	//Will return 'true' if a new game has started, 'false' otherwise
	//This one is for Dice Chooser
	public static boolean reinitialize(PlayerRecord record, int rollNumber)
	{
		if(record.availableCombinations().length == 13 && rollNumber == 1)
			return true;
		return false;
	}
	//And this one is for Combo Chooser
	public static boolean reinitialize(PlayerRecord record)
	{
		if(record.availableCombinations().length == 13)
			return true;
		return false;
	}

}
