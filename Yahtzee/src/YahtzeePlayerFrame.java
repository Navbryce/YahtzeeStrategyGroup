

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


public class YahtzeePlayerFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 84L;
	/**
	 * 
	 */
	private JPanel contentPane;
	private JPanel dicePane;
	private JPanel comboPane;
	private YahtzeeDiceButton[] diceButtons;
	private JButton reroll;
	private JLabel rerollCount;
	private int rerollInt;
	private int[] diceForCombinationScores;
	private JButton newGame;
	private YahtzeeComboButton[] comboButtons;
	private AbstractYahtzeeCombination[] combos;
	private int numCombos;
	private JLabel upperScore;
	private JLabel lowerScore;
	private boolean rerollClicked;
	private boolean comboClicked;
	private boolean newGameClicked;
	private JLabel[] scoreLabels;
	private JLabel recentScore, totalScore;
	private int lastScore;
	private boolean allUpperSectionChosen;
	private static ArrayList<ImageIcon> reg;
	private static ArrayList<ImageIcon> shaded;
	private static int lastRecordedUpperScores;
	private boolean bonusAwarded;

	YahtzeePlayerFrame(){
		numCombos = AbstractYahtzeeCombination.allCombinations().length;
		contentPane = new JPanel();
		dicePane = new JPanel();
		lastScore =0;
		lastRecordedUpperScores =0;

		setDicePaneLayout(diceButtons);

		comboClicked = false;
		comboButtons = new YahtzeeComboButton[numCombos];
		scoreLabels = new JLabel[numCombos];
		comboPane = new JPanel();
		setComboPaneLayout(comboPane,comboButtons,scoreLabels);

		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		contentPane.add(comboPane);
		recentScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		totalScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		recentScore.setText("Last Score: 0");
		totalScore.setText("Total Score: 0");
		contentPane.add(recentScore);
		contentPane.add(totalScore);
		contentPane.add(dicePane);
		contentPane.add(Box.createRigidArea(new Dimension(10, 10)));

		rerollInt = 2;
		rerollClicked = false;
		reroll = new JButton("Reroll");
		reroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		reroll.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				rerollClicked = true;
				if(rerollInt>0)
					rerollInt--; 
				rerollCount.setText(rerollInt + " remaining");
				if(rerollInt == 0)
				{
					rerollCount.setText("Select a combination");
					reroll.setEnabled(false);
				}
			} 
		});
		contentPane.add(reroll);
		rerollCount = new JLabel(rerollInt + " remaining");
		rerollCount.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(rerollCount);


		contentPane.add(Box.createRigidArea(new Dimension(10, 10)));
		newGame = new JButton("New Game");
		newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		newGame.setEnabled(false);
		newGame.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				newGameClicked = true;
				for(int i=0;i<comboButtons.length;i++){
					comboButtons[i].reset();
					scoreLabels[i].setText("     ");
				}
				allUpperSectionChosen = false;
				lastScore =0;
				recentScore.setText("Last Score: 0");
				rerollInt = 2; 
				rerollCount.setText(rerollInt + " remaining");
				newGame.setEnabled(false);
				reroll.setEnabled(true);
				repaint();
			} 
		});
		contentPane.add(newGame);

		setContentPane(contentPane);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we)
			{ 
				JFrame frame = new JFrame();
				int x = JOptionPane.showConfirmDialog(frame,"Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
				if(x == JOptionPane.YES_OPTION)
				{
					YahtzeePlayerFrame.close();
				}
			}
		});
		setLocationRelativeTo(null);
		setTitle("Yahtzee");
		setAlwaysOnTop(true);
		setAlwaysOnTop(false);
		setResizable(false);
	}
	private void setDicePaneLayout(JButton[] buttons){
		reg = new ArrayList<ImageIcon>();
		shaded = new ArrayList<ImageIcon>();
		Image img = null;
		Image img2 = null;
		for(int i=1; i<7;i++){
			try {
				switch(i){
				case 1: 
					img = ImageIO.read(new File("YahtzeeDice/dice1Shade.png"));
					img2 = ImageIO.read(new File("YahtzeeDice/dice1.png"));
					break; 
				case 2: 
					img = ImageIO.read(new File("YahtzeeDice/dice2Shade.png"));
					img2 = ImageIO.read(new File("YahtzeeDice/dice2.png"));
					break; 
				case 3: 
					img = ImageIO.read(new File("YahtzeeDice/dice3Shade.png"));
					img2 = ImageIO.read(new File("YahtzeeDice/dice3.png"));
					break; 
				case 4: 
					img = ImageIO.read(new File("YahtzeeDice/dice4Shade.png"));
					img2 = ImageIO.read(new File("YahtzeeDice/dice4.png"));
					break; 
				case 5: 
					img = ImageIO.read(new File("YahtzeeDice/dice5Shade.png"));
					img2 = ImageIO.read(new File("YahtzeeDice/dice5.png"));
					break; 
				case 6: 
					img = ImageIO.read(new File("YahtzeeDice/dice6Shade.png"));
					img2 = ImageIO.read(new File("YahtzeeDice/dice6.png"));
					break; 
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			Image resizedShade = img.getScaledInstance(95,95,Image.SCALE_SMOOTH);
			Image resizedReg = img2.getScaledInstance(95,95,Image.SCALE_SMOOTH);
			final ImageIcon Shade = new ImageIcon(resizedShade);
			final ImageIcon Reg = new ImageIcon(resizedReg);
			reg.add(Reg);
			shaded.add(Shade);
		}
		dicePane.setLayout(new BoxLayout(dicePane, BoxLayout.LINE_AXIS));
		diceButtons = new YahtzeeDiceButton[5];
		for(int i = 0; i<5; i++){
			diceButtons[i] = new YahtzeeDiceButton();
			diceButtons[i].setPreferredSize(new Dimension(100,100));
			dicePane.add(diceButtons[i]);
		}
	}
	public static ImageIcon getDiceImage(int index, boolean select){
		if(select)
			return reg.get(index);
		else
			return shaded.get(index);
	}
	private void setComboPaneLayout(JPanel pane,YahtzeeComboButton[] buttons,JLabel[] scores){
		JPanel upperMasterPane = new JPanel();
		upperMasterPane.setLayout(new BoxLayout(upperMasterPane, BoxLayout.PAGE_AXIS));
		upperMasterPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		AbstractYahtzeeCombination [] combos = AbstractYahtzeeCombination.allCombinations();
		JLabel upper = new JLabel("Upper Section");
		upper.setAlignmentX(Component.CENTER_ALIGNMENT);
		upperMasterPane.add(upper);
		JPanel upperComboPane = new JPanel();
		upperComboPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		for(int i = 0; i < 6; i++){
			c.gridy = i;
			c.gridx =0;
			String comboString = combos[i].name();
			YahtzeeComboButton comboButton = new YahtzeeComboButton(comboString);
			buttons[i] = comboButton;
			comboButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			upperComboPane.add(comboButton,c);
			scores[i] = new JLabel("     ");
			c.gridx=1;
			upperComboPane.add(scores[i],c);
		}
		upperMasterPane.add(upperComboPane);
		upperScore = new JLabel("Upper Score: 0");
		upperScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		upperMasterPane.add(upperScore);	

		JPanel lowerMasterPane = new JPanel();
		lowerMasterPane.setLayout(new BoxLayout(lowerMasterPane, BoxLayout.PAGE_AXIS));
		lowerMasterPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel lower = new JLabel("Lower Section");
		lower.setAlignmentX(Component.CENTER_ALIGNMENT);
		lowerMasterPane.add(lower);
		JPanel lowerComboPane = new JPanel();
		lowerComboPane.setLayout(new GridBagLayout());
		for(int i = 6; i < combos.length; i++){
			c.gridy=i;
			c.gridx=0;
			String comboString = combos[i].name();
			YahtzeeComboButton comboButton = new YahtzeeComboButton(comboString);
			buttons[i] = comboButton;
			comboButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			scores[i] = new JLabel("     ");
			lowerComboPane.add(comboButton,c);
			c.gridx=1;
			lowerComboPane.add(scores[i],c);
		}
		lowerMasterPane.add(lowerComboPane);
		lowerScore = new JLabel("Lower Score: 0");
		lowerScore.setAlignmentX(Component.CENTER_ALIGNMENT);
		lowerMasterPane.add(lowerScore);
		pane.setLayout(new BoxLayout(pane,BoxLayout.LINE_AXIS));
		upperMasterPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));


		pane.add(upperMasterPane);
		recentScore = new JLabel();
		totalScore = new JLabel();
		pane.add(Box.createRigidArea(new Dimension(40, 0)));
		pane.add(lowerMasterPane);

	}
	private void repaint(PlayerRecord record, int[]dice){
		for(int i = 0; i<dice.length;i++){
			diceButtons[i].set(dice[i],false);
		}

		combos = record.availableCombinations();
		if(record.upperSectionScore() >= 63)
		{
			upperScore.setText("Score: " + record.upperSectionScore() + "   Bonus awarded");
		}
		else if(allUpperSectionChosen)
		{	upperScore.setText("Score: " + record.upperSectionScore() + "   Bonus not awarded");
		}
		else
		{
			upperScore.setText("Score: " + record.upperSectionScore() + "   Difference: " + record.upDown());
		}
		lowerScore.setText("Score: " + record.lowerSectionScore());
		recentScore.setText("Last Score: " + lastScore);
		totalScore.setText("Total Score: " + record.totalScore());
		lastRecordedUpperScores= record.upperSectionScore();
	}

	public void activateRerollButton(PlayerRecord record, int[] dice){
		for(int x=0; x<diceButtons.length;x++)
		{
			diceButtons[x].enableClicking();
		}
		for(int x=0; x<comboButtons.length;x++)
		{
			comboButtons[x].disable();
		}
		repaint(record,dice);
		reroll.setEnabled(true);
		rerollClicked = false;
	}

	public void activateAvailableCombinations (PlayerRecord record, int[] dice){
		reroll.setEnabled(false);
		for(int x=0; x<diceButtons.length;x++)
		{
			diceButtons[x].disableClicking();
		}
		for(int x=0; x<comboButtons.length;x++)
		{
			if(!(comboButtons[x].isUsed()))
				comboButtons[x].enable();
		}
		for(int x=0; x< combos.length;x++)
		{
			int index = AbstractYahtzeeCombination.combinationIndex(combos[x].name());
			scoreLabels[index].setForeground(Color.RED);
			scoreLabels[index].setText("   "+combos[x].score(dice));
		}
		diceForCombinationScores = dice;
		repaint(record,dice);
		comboClicked = false;
	}

	public void activateNewGameButton (PlayerRecord record, int[] dice){
		repaint(record,dice);
		reroll.setEnabled(false);
		newGame.setEnabled(true);
		newGameClicked = false;
	}

	public boolean rerollButtonClicked(boolean[] reroll){
		if(rerollClicked){
			for(int i=0;i<reroll.length;i++){
				reroll[i] = diceButtons[i].isSelected();
			}
			return true;
		}
		else
			return false;
	}

	public int combinationChosen(){
		int index = -1; 
		YahtzeeComboButton selectedButton = new YahtzeeComboButton("");
		String clickedCombo;
		for(int x=0; x< comboButtons.length;x++){
			YahtzeeComboButton b = comboButtons[x];
			if(b.isSelected())
			{
				//Update reroll display, unless there is only one combo left
				if(combos.length > 1)
				{
					rerollInt = 2; 
					rerollCount.setText(rerollInt + " remaining");
				}
				comboClicked = true;
				selectedButton = b;
				b.nullify();
				break;
			}
		}
		if(comboClicked){
			clickedCombo = selectedButton.getName();
			for(int i = 0; i<combos.length; i++){
				if(clickedCombo.equals(combos[i].name()))
				{
					index = i;
				}
				else
				{
					int labelIndex = AbstractYahtzeeCombination.combinationIndex(combos[i].name());
					scoreLabels[labelIndex].setText("");
				}
			}
			lastScore = combos[index].score(diceForCombinationScores);
			int labelIndex = AbstractYahtzeeCombination.combinationIndex(combos[index].name());
			scoreLabels[labelIndex].setForeground(Color.BLACK);
			scoreLabels[labelIndex].setText("   "+lastScore);
			if(combos[index].upperSection() && (lastRecordedUpperScores+lastScore) >= 63 && !bonusAwarded)
			{
				lastScore += 35;
				bonusAwarded = true;
			}
		}
		return index;
	}
	public boolean[] getRerollArray(){
		boolean[] reroll = new boolean[5];
		for(int i=0; i<diceButtons.length; i++){
			reroll[i]=diceButtons[i].isSelected();
		}
		return reroll;
	}
	public boolean newGameClicked(){
		if(newGameClicked)
		{
			newGameClicked =false;
			return true;
		}
		return false;
	}
	public static void close()
	{
		System.exit(0);
	}


}
