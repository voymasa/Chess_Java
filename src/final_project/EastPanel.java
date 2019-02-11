package final_project;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EastPanel extends JPanel
{
	private int GRID_WIDTH = 1;
	private int GRID_HEIGHT = 2;
	private ButtonPanel buttonPanel;
	private static MessagePanel messagePanel;
	public GameWindow gameWindow;
	
	public EastPanel(GameWindow callback)
	{
		this.gameWindow = callback;
		this.setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
		createButtonPanel();
		createMessagePanel();
	}

	private void createMessagePanel()
	{
		messagePanel = new MessagePanel(this.getWidth(), this.getHeight());
		this.add(messagePanel);
	}

	private void createButtonPanel()
	{
		buttonPanel = new ButtonPanel(gameWindow);
		this.add(buttonPanel);
	}
	
	public static MessagePanel getMessagePanel()
	{
		return messagePanel;
	}
}
