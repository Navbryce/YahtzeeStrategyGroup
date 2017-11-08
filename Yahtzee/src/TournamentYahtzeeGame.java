

public class TournamentYahtzeeGame
{

	public double playGame(Strategy player)
	{
		double score=0;
		boolean notBroken = true;
		try{
			//Runs 100,000 games and averages the score
			score = YahtzeePlayerEvaluator.evaluatePlayer((AbstractComputerYahtzeePlayer)player,100000);
		}catch(Exception e)
		{
			System.err.println("The Strategy has encountered an uncaught exception that it  Here is a handy stack trace so you can fix it:\n\n");
			e.printStackTrace();
			System.out.println("I would suspect the exception is likely indexOutOfBounds, in which case you're probably trying to select combinations");
			System.out.println("incorrectly, and you should look at that in the stack trace, or it's a nullPointer exception, in which case you likely have");
			System.out.println("not actually initialized an array, or something weird is happening and you should check the stack trace.\n");
			System.out.println("Seriously. The stack trace is your friend.");
			notBroken = false;
		}
		if(notBroken)
		{
			return score;
		}
		return -1;
	}

	public static void main(String [] args)
	{
		TournamentYahtzeeGame tester = new TournamentYahtzeeGame();
		System.out.println(tester.playGame(new YahtzeeTestStrategy()));
	}
}
