package final_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Aaron Voymas
 * @since 2 Dec 2015
 */
public class GameWindow extends JFrame
{
	// the panels to hold the various window containers
	private ChessPanel chessPanel;
	private EastPanel eastPanel;
	private CapturePanel capturePanel;
	private JMenuBar menuBar;
	
	// desired frame width and height
	private static final int FRAME_WIDTH = 1024;
	private static final int FRAME_HEIGHT = 768;
	
	// player colors
	private static Color p1Color;
	private static Color p2Color;
	
	// getters for player colors
	public static Color getP1Color()
	{
		return p1Color;
	}
	public static Color getP2Color()
	{
		return p2Color;
	}
	
	// Scanner and PrintWriter for use with save/load
	public Scanner inFile;
	public PrintWriter outFile;
	public String fileName; // fileName to save or load
	
 	public GameWindow()
	{
		// set player colors
		p1Color = Color.WHITE;
		p2Color = Color.BLACK;
		
		// construct menu bar
		createMenuBar();
		
		// create Window Panels
		createEastPanel();
		createChessBoard();
		createCapturePanel();
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	
 	/**
 	 * Create the chesspanel and place in the center
 	 */
	private void createChessBoard()
	{
		chessPanel = new ChessPanel();
		this.add(chessPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Create east panel and place it in the east
	 */
	private void createEastPanel()
	{
		eastPanel = new EastPanel(this);
		this.add(eastPanel, BorderLayout.EAST);
	}
	
	/**
	 * Create the panel that shows what pieces have been captured
	 */
	private void createCapturePanel()
	{
		capturePanel = new CapturePanel();
		this.add(capturePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Create the menu bar
	 */
	private void createMenuBar()
	{
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// add the menus
		menuBar.add(createFileMenu());
		menuBar.add(createOptionMenu());
		menuBar.add(createHelpMenu());
	}

	/**
	 * Creat the help menu
	 * @return returns the help menu
	 */
	private JMenu createHelpMenu()
	{
		JMenu help = new JMenu("Help");
		help.add(createHelpItem("Directions"));
		help.add(createHelpItem("About"));
		
		return help;
	}

	/**
	 * Create the help menu items
	 * @param string the menu name
	 * @return returns the menu item
	 */
	private JMenuItem createHelpItem(String string)
	{
		class helpMenuListener implements ActionListener
		{
			public void actionPerformed(ActionEvent action)
			{
				String selected = action.getActionCommand();
				String message, title;
				
				if(selected.equals("Directions"))
				{
					title = "Directions";
					message = "The player selects a piece on their turn and" +
					          "\nthen selects a space to move the piece. Legal" +
							    "\nmoves for the piece, will be highlighted." +
					          "\nLegal Moves:"+
							    "\nPawn: forward 1 space, attack diagonally"+
					          "\nRook: forward/back or laterally across the board"+
							    "\nKnight: L-shape, two by one"+
					          "\nBishop: diagonally across the board"+
							    "\nQueen: as both rook and bishop"+
					          "\nKing: as both rook and bishop, but only one space";
					showMessageBox(title, message);
				}
				else if(selected.equals("About"))
				{
					title = "About";
					message = "Author: Aaron Voymas\nCreated: Dec 2015\nJava 7";
					showMessageBox(title, message);
				}
			}

			/**
			 * Creates a message window
			 * @param title the title of the window
			 * @param message the message to display
			 */
			private void showMessageBox(String title, String message)
			{
				JFrame about = new JFrame();
				about.setSize(360, 240);
				about.setTitle(title);
				about.setVisible(true);
				
				// information to display
				JTextArea info = new JTextArea();
				info.setText(message);
				about.add(info);
			}
		}
		JMenuItem item = new JMenuItem(string);
		ActionListener listener = new helpMenuListener();
		item.addActionListener(listener);
		
		return item;
	}

	/**
	 * Creates the options menj
	 * @return returns the options menu
	 */
	private JMenu createOptionMenu()
	{
		JMenu optionMenu = new JMenu("Options");
		optionMenu.add(createOptionItem("Player 1 Color"));
		
		return optionMenu;
	}

	/**
	 * Create the options menu items
	 * @param string the menu item
	 * @return returns the menu item
	 */
	private JMenuItem createOptionItem(String string)
	{
		JMenu menu = new JMenu(string);
		menu.add(createColorItem("White"));
		menu.add(createColorItem("Black"));
		
		return menu;
	}

	private JMenuItem createColorItem(String string)
	{
		class optionMenuListener implements ActionListener
		{
			public void actionPerformed(ActionEvent action)
			{
				String selected = action.getActionCommand();
				
				if(selected.equals("White"))
				{
					p1Color = Color.WHITE;
					p2Color = Color.BLACK;
					((CapturePanel) capturePanel).getLblP1().setText("Player 1 (WHITE): ");
					((CapturePanel) capturePanel).getLblP2().setText("Player 2 (BLACK): ");
				}
				else if(selected.equals("Black"))
				{
					p1Color = Color.BLACK;
					p2Color = Color.WHITE;
					((CapturePanel) capturePanel).getLblP1().setText("Player 1 (BLACK): ");
					((CapturePanel) capturePanel).getLblP2().setText("Player 2 (WHITE): ");
				}
			}
		}
		JMenuItem item = new JMenuItem(string);
		ActionListener listener = new optionMenuListener();
		item.addActionListener(listener);
		
		return item;
	}

	/**
	 * Sets up the save file by name
	 * @return returns the filename
	 */
	public void setupSaveFile()
	{
		// setup a save window
		JFrame saveFrame = new JFrame();
		saveFrame.setSize(300, 70);
		saveFrame.setTitle("Save Game");
		saveFrame.setVisible(true);
		
		// add a label
		JLabel lblSave = new JLabel();
		lblSave.setText("Save Name: ");
		saveFrame.add(lblSave, BorderLayout.WEST);
		
		// add a text field
		JTextField text = new JTextField();
		text.setEditable(true);
		saveFrame.add(text, BorderLayout.CENTER);
		
		/**
		 * The listener for the button to save by the name
		 */
		class SaveButtonListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent button)
			{
				try
				{
					fileName = "/src/" + text.getText() + ".txt";
					outFile = new PrintWriter(fileName);
				} catch (FileNotFoundException e)
				{
					System.out.println("File Name: " + fileName);
					e.printStackTrace();
				}
			}	
		}
		// add save button
		JButton saveBtn = new JButton("Save");
		ActionListener listener = new SaveButtonListener();
		saveBtn.addActionListener(listener);
		saveFrame.add(saveBtn, BorderLayout.EAST);
	}
	
	/**
	 * Creates the file menu
	 * @return returns the menu
	 */
	private JMenu createFileMenu()
	{
		class FileMenuListener implements ActionListener
		{
			public void actionPerformed(ActionEvent action)
			{
				String selected = action.getActionCommand();
				
				if(selected.equals("New Game"))
				{
					restart();
				}
				else if(selected.equals("Save Game"))
				{
					setupSaveFile();
					
					// the information to output
					String file = "";

					file = file + getP1Color() + "\n"; // add player 1's color
					file = file + getP2Color() + "\n"; // add player 2's color
					file = file + chessPanel.getTurn() + "\n"; // add the current player's turn
					
					// stores the current chessboard for saving
					ChessBoard[][] board = chessPanel.getChessBoard();
					
					// for each row in the chessboard
					for(int row = 0; row < board.length; row++)
					{
						// for each column in the chessboard
						for(int col = 0; col < board[row].length; col++)
						{
							if(board[row][col].getPiece() instanceof Pawn)
							{
								file = file + "1 "; // pawn spot
							}
							else
								file = file + "0 "; // not pawn spot
						}
						file = file + "\n";
					}
					
					outFile.print(file);
					outFile.close();
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
		JMenu fileMenu = new JMenu("File");
		ActionListener listener = new FileMenuListener();
		
		JMenuItem newGameItem = new JMenuItem("New Game");
		newGameItem.addActionListener(listener);
		fileMenu.add(newGameItem);
		
		JMenuItem saveGameItem = new JMenuItem("Save Game");
		saveGameItem.addActionListener(listener);
		fileMenu.add(saveGameItem);
		
		JMenuItem loadGameItem = new JMenuItem("Load Game");
		loadGameItem.addActionListener(listener);
		fileMenu.add(loadGameItem);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(listener);
		fileMenu.add(exitItem);
		
		return fileMenu;
	}

	// resets the whole board
	public void restart()
	{
		this.remove(chessPanel);
		// reset all of the chess pieces
		createChessBoard();
	}
	
}
