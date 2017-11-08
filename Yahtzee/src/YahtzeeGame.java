

import java.util.Arrays;

public class YahtzeeGame {
	
	private int[] dice;
	
	public void sortDice (int[] dice){
		 Arrays.sort(dice);
	}
	public void playerRoll(YahtzeePlayer player, PlayerRecord record, int [] dice, int rollNumber){
		PlayerRecord recordCopy = new PlayerRecord(record);
		boolean[] rollBoolean = new boolean[5];
		int[] referenceDice = new int[dice.length];
		//You can't manually change the dice values to whatever you want now. Ha.
		System.arraycopy(dice, 0, referenceDice, 0, dice.length);
		player.reroll(referenceDice,rollNumber,recordCopy,rollBoolean);
		for(int x=0; x<dice.length;x++)
		{
			if(rollBoolean[x])
				dice[x] = ((int)(Math.random()*6)) + 1;
		}
		sortDice(dice);
	}
	
	public void playerChoose(YahtzeePlayer player, PlayerRecord record, int [] dice, int rollNumber){
		PlayerRecord recordCopy = new PlayerRecord(record);
		int[] referenceDice = new int[dice.length];
		System.arraycopy(dice, 0, referenceDice, 0, dice.length);
		int choice = player.chooseCombination(referenceDice,recordCopy);
		int comboLength = record.availableCombinations().length;
		AbstractYahtzeeCombination combo;
		if(choice < 0)
			choice = 0; 
		if(choice >= comboLength)
			choice = comboLength - 1; 
		combo = record.availableCombinations()[choice];
		record.chooseCombination(choice, combo.score(dice)); 
	}
	
	public void yahtzeeTurn(YahtzeePlayer player, PlayerRecord record){
		//The initial roll
		for(int x=0; x<dice.length;x++)
		{
			dice[x] = ((int)(Math.random()*6)) + 1;
		}
		sortDice(dice);
		for(int x=1; x<3;x++)
		{
			playerRoll(player, record, dice, x);
		}
		playerChoose(player, record, dice, 3);
	}
	public int yahtzeeGame(YahtzeePlayer player){
		dice = new int[5];
		PlayerRecord record = new PlayerRecord();
		for(int i = 0; i<13; i++){
			yahtzeeTurn(player,record);
		}
		player.finalResults(dice, record);
		return record.totalScore();
	}
}
