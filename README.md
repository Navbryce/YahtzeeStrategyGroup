# YahtzeeStrategyGroup
**Note: _bplunk0244_ is _navbryce_ **
Several Yahztee strategies can be found in this repository. One of these strategies aims to solve yahtzee through "scenario-based" reactions ("I have these cards, so I should do this"); *StrategyOne.java*. Another strategy attempts to use probabilities and brute force to solve the problem; *ProbabilityStrategy.java*. Every possible outcome is calculated and the likelihood of each outcome, the strategy tries to take the outcome with the greatest the potential. The "turn" depth of these outcomes can be controlled.

# Demo
The strategies extend an abstract class that integrates with a Yahztee testbed also found in the repository. To run them, go to the YahzteePlayerEvaluation.java class. Parameters, such as the number of games the strategy to play and which strategy to use, can be easily changed in the *main* method.
