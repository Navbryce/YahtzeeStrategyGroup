import java.util.ArrayList;
import java.util.HashMap;



public class ProbabilityStrategy extends StrategyOne{
	private ArrayList<boolean[]> allRerollCombinations;
	private final int[] combinationMaxScores = {5,10,15,20,25,30,30,30,25,30,40,50,30};
	

	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
			double max=-1;
			boolean[] maxReroll=null;
			double[] scoresForEachReroll = new double[allRerollCombinations.size()];

			for(boolean[] rerollCombination: allRerollCombinations){

				double averageScore = generateAverage(rerollCombination, dice, 3-rollNumber, record);
				//System.out.println(averageScore);
				if(averageScore>max){
					max=averageScore;
					maxReroll=rerollCombination;
					System.out.println(max);
				}
			}
			for(boolean rerollValue: reroll){
				System.out.println(rerollValue);
			}
			System.out.println();
			
			reroll=maxReroll;
	}
	private boolean[] keepValueOfDie(boolean[] reroll, int[] dice, int dieFaceValue){
		for(int diceIndex=0; diceIndex<dice.length; diceIndex++){
			int dieValue = dice[diceIndex];
			if(dieValue==dieFaceValue){
				reroll[diceIndex]=false; //False means keep

			}
		}
		return reroll;
	}

	public int chooseCombination(int[] dice, PlayerRecord record) {
		for(int i=0;i<dice.length;i++){
			//System.out.println("Dice: "+dice[i]);
		}
		AbstractYahtzeeCombination[] scoreCombinations = record.rawCombinations();
	
		double maxRatio=-1;
		int selectedIndex=-1;
		int maxValueIndex=-1;
		int maxValue=-1;
		int maxUpperSectionScore=-1;
		int maxUpperSectionIndex=-1;
		int bestUpperSectionRatio=-1;
		int bestUpperSectionIndex=-1;
		int maxRatioIndex=-1;
		int amountNeededForBonus=63-record.upperSectionScore();
		
		for(int combinationIndex=0;combinationIndex<scoreCombinations.length;combinationIndex++){
			if(scoreCombinations[combinationIndex]!= null){
				//System.out.println("Index:"+combinationIndex + "  Score:"+scoreCombinations[combinationIndex].score(dice));
				double scoreRatio = ((double)scoreCombinations[combinationIndex].score(dice))/((double)combinationMaxScores[combinationIndex]);
				AbstractYahtzeeCombination combination = scoreCombinations[combinationIndex];
				int score = combination.score(dice);
				//System.out.println("Combination " + combinationIndex + ":" + scoreRatio);
				if(scoreRatio>maxRatio){
					maxRatio = scoreRatio;
					maxRatioIndex = combinationIndex;
				}
				if(scoreCombinations[combinationIndex].score(dice)>maxValue){
					maxValue=scoreCombinations[combinationIndex].score(dice);
					maxValueIndex=combinationIndex;
				}
				if(combinationIndex<=5 && scoreCombinations[combinationIndex].score(dice)>maxUpperSectionScore){
					maxUpperSectionScore=scoreCombinations[combinationIndex].score(dice);
					maxUpperSectionIndex=combinationIndex;
				}
				if(score>=amountNeededForBonus && scoreRatio>bestUpperSectionRatio){
					bestUpperSectionIndex=combinationIndex;
				}
			}
		}
		if(maxValue>=30){
			selectedIndex=maxValueIndex;
			//System.out.println("SELECTED MAX VALUE");

		}else if(amountNeededForBonus>0 && maxUpperSectionScore>=amountNeededForBonus){
			selectedIndex=maxUpperSectionIndex;
		}else if(maxRatio<=.3){
			boolean selectedIndexBoolean=false;
			for(int combinationIndex=0;combinationIndex<scoreCombinations.length && !selectedIndexBoolean;combinationIndex++){
				try{
					//System.out.println("SCORE: " + scoreCombinations[combinationIndex]);
					if(scoreCombinations[combinationIndex]!=null){ //AKA we already selected it
						//System.out.println("SELECTED SMALLEST: " + combinationIndex);
						selectedIndex=combinationIndex;
						selectedIndexBoolean=true;
					}
					}catch(Exception ex){
					
				}
			}
			//System.out.println("SELECTED SMALLEST COMBINATION");

		}else{
			selectedIndex=maxRatioIndex;
			//System.out.println("SELECTED MAX RATIO");
		}
		selectedIndex = rawToAvailable(record.availableCombinations(), scoreCombinations[selectedIndex]);
		//System.out.println("Selected Index: " + selectedIndex +"--Score: " + record.availableCombinations()[selectedIndex] + " Name: ");
		return selectedIndex;

	}

	@Override
	public void finalResults(int[] dice, PlayerRecord record) {
		// TODO Auto-generated method stub
		
	}


	public String author() {return " ";}
	public String playerName() {return " ";}
	private int rawToAvailable(AbstractYahtzeeCombination[] availableCombinations, AbstractYahtzeeCombination selectedCombination){
		int indexOfAvailable=-1;
		//System.out.println("Output:" + indexOfAvailable + ":" + availableCombinations[indexOfAvailable].name());
		return nameToAvailable(availableCombinations, selectedCombination.name());
	}
	private int nameToAvailable(AbstractYahtzeeCombination[] availableCombinations, String selectedCombinationName){
		int indexOfAvailable=-1;

		for(int combinationCounter=0; combinationCounter<availableCombinations.length && indexOfAvailable==-1; combinationCounter++){
			try{
				if(selectedCombinationName.equals(availableCombinations[combinationCounter].name())){
					indexOfAvailable=combinationCounter;
				}
			}catch(Exception ex){
				System.out.println("an error occurred");
				ex.printStackTrace();
			}
		}
		//System.out.println("Output:" + indexOfAvailable + ":" + availableCombinations[indexOfAvailable].name());
		return indexOfAvailable;
	}
	private ArrayList<Straight> checkForStraight(int[] dice){ 
		for(int value: dice){
			//System.out.println("Dice Value: " + value);
		}
		
		ArrayList<Straight> straights = new ArrayList();
		
		int previousValue=-1;
		int startingIndex=0;
		for(int diceCounter=0; diceCounter<dice.length; diceCounter++){
			int currentValue=dice[diceCounter];
		
			if(previousValue!=-1 && previousValue+1!=currentValue){ //The straight has terminated
				int size=diceCounter-startingIndex;
				Straight straight = new Straight(size, startingIndex);
				straights.add(straight);
				startingIndex=diceCounter;
				if(diceCounter==dice.length-1){ //If a new straight starts at the final index (AKA a straight of size one)
					Straight endStraight = new Straight(1, startingIndex);
					straights.add(endStraight);

					
				}
			}else if(diceCounter==dice.length-1){ //The straight continues until the end
				int size = diceCounter-startingIndex+1;
				Straight straight = new Straight(size, startingIndex);
				straights.add(straight);
				
			}
		
			previousValue=currentValue;
		}
		
		for(Straight straight: straights){
			//System.out.println("Straight size: " + straight.getSize() + " Starting Index:" + straight.getStraightStartingIndex());
		}
		return straights;
		
	}
	private boolean[] keepRangeOfValues(boolean[] reroll, int startingIndex, int size){
		int indexCounter=startingIndex;
		for(int sizeCounter=size; sizeCounter>0; sizeCounter--){
			reroll[indexCounter]=false;
			indexCounter++;
		}
		return reroll;
	}

	public ArrayList<int[]> getDiceCombinations(int[] diceParameter, boolean[] reroll){
		ArrayList<int[]> diceCombinations = new ArrayList();
		ArrayList<Integer> rerollIndexes=getRerollIndexes(reroll);
		int counter=0;
		int[] dice = {1, 1, 1, 1, 1,}; //Clean this up
		int[] rerollDice=new int[rerollIndexes.size()];
		for(int rerollIndex: rerollIndexes){
			dice[rerollIndex]=1;
			rerollDice[counter]=1; //For the conditional below
			counter++;
		}

		while(!allEqualToSix(rerollDice)){
			
			boolean incrementNextIndex=false;
			for(int rerollIndex=rerollIndexes.size()-1; rerollIndex>=0 && (rerollIndex==rerollIndexes.size()-1 || incrementNextIndex); rerollIndex--){
				if(incrementNextIndex){
					dice[rerollIndexes.get(rerollIndex)]+=1;
					incrementNextIndex=false;
				}
				if(dice[rerollIndexes.get(rerollIndex)]==7){
					dice[rerollIndexes.get(rerollIndex)]=1;
					incrementNextIndex=true;
						
				}
			}
			diceCombinations.add(dice.clone());
			dice[rerollIndexes.get(rerollIndexes.size()-1)]=dice[rerollIndexes.get(rerollIndexes.size()-1)]+1; //The main increment

			for(int rerollIndexCounter=0; rerollIndexCounter<rerollIndexes.size(); rerollIndexCounter++){

				rerollDice[rerollIndexCounter]=dice[rerollIndexes.get(rerollIndexCounter)];
			}
			
		}
		diceCombinations.add(dice.clone());

		for(int[] diceCombination: diceCombinations){
			for(int i=0;i<diceCombination.length;i++){
				//System.out.print(diceCombination[i]);
			}
			//System.out.println();
		}
		return diceCombinations;
	}
	
	/**
	 * 
	 * @param reroll
	 * @return Returns all the possible reroll options
	 */
	public ArrayList<boolean[]> getRerollCombinations(boolean[] rerollParameter){
		ArrayList<boolean[]> rerollCombinations = new ArrayList();

		int counter=0;
		boolean[] reroll = {false, false, false, false, false};
		while(!allEqualTrue(reroll)){

			rerollCombinations.add(reroll.clone());

		   	boolean incrementNextIndex=false;
			for(int rerollCounter=reroll.length-1; rerollCounter>=0 && (rerollCounter==reroll.length-1 || incrementNextIndex); rerollCounter--){
				if(incrementNextIndex){
					reroll[rerollCounter]=!reroll[rerollCounter];
					incrementNextIndex=false;
				}
				if((reroll[rerollCounter] && rerollCounter==reroll.length-1) || (rerollCounter<reroll.length-1 && !reroll[rerollCounter])){
					reroll[rerollCounter]=false;
					incrementNextIndex=true;
						
				}else if(rerollCounter==reroll.length-1){
					reroll[reroll.length-1]=true; 
				}
			}
			counter++;
			
		}
		//rerollCombinations.add(reroll.clone()); //DONT ADD AL TRUES


		for(boolean[] rerollCombination: rerollCombinations){
			for(int i=0;i<rerollCombination.length;i++){
				//System.out.print(rerollCombination[i]);
			}
			//System.out.println();
		}
		return rerollCombinations;

		
	}
	private ArrayList<Integer> getRerollIndexes(boolean[] reroll){
		ArrayList<Integer> rerollIndexes=new ArrayList();
		for(int booleanCounter=0; booleanCounter<reroll.length; booleanCounter++){
			if(reroll[booleanCounter]){
				rerollIndexes.add(booleanCounter);
			}
		}
		return rerollIndexes;
	}
	private boolean allEqualToSix(int[] dice){
		boolean allEqualToSix=true;
		for(int diceCounter=0; diceCounter<dice.length && allEqualToSix; diceCounter++){
			if(dice[diceCounter]!=6){
				allEqualToSix=false;
			}
		}
		return allEqualToSix; 
	}
	private boolean allEqualTrue(boolean[] reroll){
		boolean allEqualToTrue=true;
		for(int rerollCounter=0; rerollCounter<reroll.length && allEqualToTrue; rerollCounter++){
			if(!reroll[rerollCounter]){
				allEqualToTrue=false;
			}
		}
		return allEqualToTrue;
	}
	/**
	 * 
	 * @param reroll
	 * @param dice the dice
	 * @param rerollsLeft numbers of rerolls left
	 * @return
	 */
	
	public static void main(String args[]){
		ProbabilityStrategy strategy = new ProbabilityStrategy();

	}
	
	public ProbabilityStrategy(){
		boolean[] reroll = {false, false, false, false, false};

		allRerollCombinations = getRerollCombinations(reroll);
		/*
		int[] dice = {1, 2, 3, 4, 5};
		PlayerRecord record = new PlayerRecord();
		for(boolean[] rerollCombination: allRerollCombinations){
			/*
			for(boolean rerollValue: rerollCombination){
				System.out.print(rerollValue);
			}
		
			//System.out.println(generateAverage(rerollCombination, dice, 2, record)); 
		}
			*/

	}
	public double generateAverage(boolean[] reroll, int[] dice, int rerollsLeft, PlayerRecord record){
		double averageScore;

		if(rerollsLeft == 0){
			AbstractYahtzeeCombination[] combinations = record.availableCombinations();
			double sumOfAverages=0;
			int scoreCounter=0;
			for(int combinationCounter=0; combinationCounter<combinations.length; combinationCounter++){
				if(combinations[combinationCounter] != null){
					sumOfAverages += combinations[combinationCounter].score(dice);
				}else{
					sumOfAverages += 0;
				}
				scoreCounter++;
			}

			averageScore = (double)((double)sumOfAverages/(double)scoreCounter);

		}else{

			double sumOfAverages=0;
			int counter=0;
			ArrayList<int[]> diceCombinations = getDiceCombinations(dice, reroll);

			for(int[] diceCombination: diceCombinations){
				if(rerollsLeft==1){
					boolean[] unimportantReroll = {false, false, false, false, false};
					sumOfAverages += generateAverage(unimportantReroll, diceCombination, 0, record);
					counter++;

				}else{
					for(boolean[] rerollCombination: allRerollCombinations){
						/*for(boolean rerollValue: rerollCombination){
							System.out.print(rerollValue);
						}
						System.out.println();
						*/
						/*for(int diceValue: diceCombination){
							System.out.print(diceValue);
						}
						System.out.println();
						*/
						sumOfAverages+=generateAverage(rerollCombination, diceCombination, rerollsLeft-1, record);
						counter++;
					}
				}


			}

			averageScore = ((double)sumOfAverages)/((double)counter);
		}
		return averageScore;

	}
	
	public class Straight{
		private int straightSize;
		private int straightStartingIndex;
		private int firstValue;
		private int lastValue;
		
		public Straight (int straightSizeValue, int straightStartingIndexValue){
			straightSize=straightSizeValue;
			straightStartingIndex=straightStartingIndexValue;
		}
		public int getSize(){
			return straightSize;
		}
		public int getStraightStartingIndex(){
			return straightStartingIndex;
		}
		public void setSize(int sizeValue){
			straightSize=sizeValue;
		}
		public void setStartingIndex(int startingIndexValue){
			straightStartingIndex=startingIndexValue;
		}
		
	}


}

