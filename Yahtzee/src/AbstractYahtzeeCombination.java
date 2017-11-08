

public abstract class AbstractYahtzeeCombination {

	private static AbstractYahtzeeCombination[] combinations = {new AcesCombination(),
			new TwosCombination(), new ThreesCombination(), new FoursCombination(),
			new FivesCombination(), new SixesCombination(), new ThreeOfAKindCombination(),
			new FourOfAKindCombination(), new FullHouseCombination(),
			new SmallStraightCombination(), new LargeStraightCombination(),
			new YahtzeeCombination(), new ChanceCombination()
	};
	
	
	public abstract int score (int [] dice);
	
	public abstract String name();
	
	public abstract String description();
	
	public abstract boolean upperSection();
	
	public static AbstractYahtzeeCombination[] allCombinations()
	{
		return combinations;
	}
		
	public static int combinationIndex(String combinationName)
	{
		for(int x=0; x<combinations.length;x++)
		{
			if(combinations[x].name().equals(combinationName))
			{
				return x;
			}
		}
		return -1;
	}
}
