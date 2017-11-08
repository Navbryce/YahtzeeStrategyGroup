

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class YahtzeeDiceButton extends JButton implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 84L;
	/**
	 * 
	 */
	private boolean selected;
	private int index;
	
	private boolean enabled;
	
	YahtzeeDiceButton()
	{
		super();
		selected = false;
		enabled = true;
		Image blankImg = null;
		try {
			blankImg = ImageIO.read(new File("YahtzeeDice/blank.png"));	
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ImageIcon blank = new ImageIcon(blankImg);
		setIcon(blank);
		addActionListener(this);
	}
	public boolean isSelected(){
		return selected;
	}
	public int getNum(){
		return index+1;
	}
	public void roll(){
		index = (int)(Math.random()*6);
		setIcon(YahtzeePlayerFrame.getDiceImage(index,false));
	}
	public void set(int num, boolean select){
		index = num-1;
		if(select){
			selected = true; 
			setIcon(YahtzeePlayerFrame.getDiceImage(index,true));
		}
		else{
			selected = false; 
			setIcon(YahtzeePlayerFrame.getDiceImage(index,false));
		}
	}
	public void actionPerformed(ActionEvent arg0) {
		if(enabled)
		{
			if(selected){
				selected = false;
				setIcon(YahtzeePlayerFrame.getDiceImage(index,false));
			}
			else{
				selected = true;
				setIcon(YahtzeePlayerFrame.getDiceImage(index,true));
			}
		}
	}
	
	public void enableClicking()
	{
		enabled = true;
	}
	
	public void disableClicking()
	{
		enabled=false;
	}
}
