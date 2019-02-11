package final_project;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

/**
 * 
 * @author Aaron Voymas
 * @since 2 Dec 2015
 */
public class GameRunner
{
	public static void main(String[] args) throws FileNotFoundException
	{
		GameWindow frame = new GameWindow();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Voymas Chess");
		frame.setVisible(true);
		
		//((GameWindow) frame).draw();
	}
}
