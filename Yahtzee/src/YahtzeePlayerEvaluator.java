

public class YahtzeePlayerEvaluator {

	
	public static double evaluatePlayer(YahtzeePlayer player, int games)
	{
		double totalScore=0;
		YahtzeeGame game = new YahtzeeGame();
		for(int x=0; x<games;x++)
		{
			totalScore += game.yahtzeeGame(player);
		}
		return (totalScore / games);
	}
	public static void main(String[] args){
		YahtzeeComputerStrategy test = new StrategyOne();
		int num = 10000;
		System.out.print("Number of Games: "+num+"   Average Score: "+evaluatePlayer(test,num));
	}
}
