package final_project;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel
{
	private int GRID_WIDTH = 1;
	private int GRID_HEIGHT = 5;
	
	private JButton btnNewGame;
	private JButton btnSaveGame;
	private JButton btnLoadGame;
	private JButton btnExit;
	
	private JLabel lblTurn; // player turn
	private GameWindow gameWindow;
	
	public ButtonPanel(GameWindow callBack)
	{
		this.gameWindow = callBack;
		this.setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
		createButtons();
		lblTurn = new JLabel("Player Turn");
		this.add(lblTurn);
	}

	private void createButtons()
	{
		class ButtonListener implements ActionListener
		{
			public void actionPerformed(ActionEvent button)
			{
				String selected = button.getActionCommand();
				
				if(selected.equals("New Game"))
				{
					gameWindow.restart();
				}
				else if(selected.equals("Save Game"))
				{
					
				}
				else if(selected.equals("Load Game"))
				{
					
				}
				else if(selected.equals("Exit"))
				{
					System.exit(0);
				}
			}
		}
		ActionListener listener = new ButtonListener();
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(listener);
		btnSaveGame = new JButton("Save Game");
		btnSaveGame.addActionListener(listener);
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(listener);
		btnExit = new JButton("Exit");
		btnExit.addActionListener(listener);
		
		this.add(btnNewGame);
		this.add(btnSaveGame);
		this.add(btnLoadGame);
		this.add(btnExit);
	}

	/**
	 * @return the lblTurn
	 */
	public JLabel getLblTurn()
	{
		return lblTurn;
	}

	/**
	 * @param lblTurn the lblTurn to set
	 */
	public void setLblTurn(JLabel lblTurn)
	{
		this.lblTurn = lblTurn;
	}
}
