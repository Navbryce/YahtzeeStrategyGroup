import java.util.ArrayList;

public class StrategyOne extends YahtzeeComputerStrategy{
	private final int[] combinationMaxScores = {5,10,15,20,25,30,30,30,25,30,40,50,30};
	public void reroll(int[] dice, int rollNumber, PlayerRecord record, boolean[] reroll) {
		for(int rerollCounter=0; rerollCounter<reroll.length; rerollCounter++){
			reroll[rerollCounter]=true;
		}
		

		int[] multiplesList = new int[6];
		for(int i=0;i<dice.length;i++){
			multiplesList[dice[i]-1]+=1;
		}
		for(int i=0;i<dice.length;i++){
			if(multiplesList[i]==1 || record.combinationScores()[i]!=-1){
				multiplesList[i] = -1;
				//System.out.println("Remove from M list:" + i);
			}
		}
		ArrayList<Integer> indexes = new <Integer>ArrayList();
		int count = 0; //Number of multiples count
		for(int i=0;i<multiplesList.length;i++){
			if(multiplesList[i]>1){
				count++;
				indexes.add(i);
			}		
		}
		
		ArrayList<Straight> straights = checkForStraight(dice);
		Straight maxStraight=null;
		for(Straight straight: straights){
			if(maxStraight==null || straight.getSize()>maxStraight.getSize()){
				maxStraight=straight;
			}
		}
			
		
		
		AbstractYahtzeeCombination[] rawCombinations = record.rawCombinations();
		if(count>1 && nameToAvailable(record.availableCombinations(), "FullHouseCombination")!=-1){
			for(int i=0;i<indexes.size();i++){
				int index = indexes.get(i); //Index of the die value (the number on the die)
				reroll=keepValueOfDie(reroll, dice, index+1); //index translated down by 1
			}
		}else if((nameToAvailable(record.availableCombinations(), "LargeStraightCombination")!=-1 || nameToAvailable(record.availableCombinations(), "SmallStraightCombination")!=-1) && maxStraight.getSize()>3){
			reroll=keepRangeOfValues(reroll, maxStraight.getStraightStartingIndex(), maxStraight.getSize());
		}else{
			int maxPossibleScore=-1;
			int dieValue=-1; //The number of circles on the face of the die
			for(int dieValueCounter=0; dieValueCounter<multiplesList.length; dieValueCounter++){
				int possibleScore=(dieValueCounter+1)*multiplesList[dieValueCounter];
				if(possibleScore>maxPossibleScore){
					maxPossibleScore=possibleScore;
					dieValue=dieValueCounter;
				}
			}
			reroll=keepValueOfDie(reroll, dice, dieValue+1); //Dievalue translated by 1

		}
		//System.out.println("Roll number: "+rollNumber);
		int diceCounter=0;
		for(boolean roll: reroll){
			//System.out.println("REROLL: " + roll + " - Die: " + dice[diceCounter]);
			diceCounter++;
		}
		//System.out.println();
		
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
		int maxRatioIndex=-1;
		for(int combinationIndex=0;combinationIndex<scoreCombinations.length;combinationIndex++){
			if(scoreCombinations[combinationIndex]!= null){
				//System.out.println("Index:"+combinationIndex + "  Score:"+scoreCombinations[combinationIndex].score(dice));
				double scoreRatio = ((double)scoreCombinations[combinationIndex].score(dice))/((double)combinationMaxScores[combinationIndex]);
				//System.out.println("Combination " + combinationIndex + ":" + scoreRatio);
				if(scoreRatio>maxRatio){
					maxRatio = scoreRatio;
					maxRatioIndex = combinationIndex;
				}
				if(scoreCombinations[combinationIndex].score(dice)>maxValue){
					maxValue=scoreCombinations[combinationIndex].score(dice);
					maxValueIndex=combinationIndex;
				}
			}
		}
		if(maxValue>=30){
			selectedIndex=maxValueIndex;
			//System.out.println("SELECTED MAX VALUE");

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

