package final_project;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MessagePanel extends JPanel
{
	private static JTextArea txtMessage;
	private JScrollPane scrollPane;
	
	public MessagePanel(int w, int h)
	{
		txtMessage = new JTextArea(12, 20);
		scrollPane = new JScrollPane(txtMessage);
		this.add(scrollPane);
		txtMessage.append("Messages: \n");
		txtMessage.setEditable(false);
	}
	
	public static JTextArea getText()
	{
		return txtMessage;
	}
}
