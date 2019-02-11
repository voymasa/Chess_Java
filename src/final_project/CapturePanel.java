package final_project;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CapturePanel extends JPanel
{
	private int GRID_WIDTH = 2;
	private int GRID_HEIGHT = 2;
	private JLabel lblP1;
	private JLabel lblP2;
	private JTextField txtP1;
	private JTextField txtP2;
	
	public CapturePanel()
	{
		this.setLayout(new GridLayout(GRID_WIDTH, GRID_HEIGHT));
		lblP1 = new JLabel("Player 1 (WHITE): ");
		txtP1 = new JTextField("");
		txtP1.setEnabled(false);
		this.add(lblP1);
		this.add(txtP1);
		
		lblP2 = new JLabel("Player 2 (BLACK): ");
		txtP2 = new JTextField("");
		txtP2.setEnabled(false);
		this.add(lblP2);
		this.add(txtP2);
	}

	/**
	 * @return the lblP1
	 */
	public JLabel getLblP1()
	{
		return lblP1;
	}

	/**
	 * @return the lblP2
	 */
	public JLabel getLblP2()
	{
		return lblP2;
	}

	/**
	 * @return the txtP1
	 */
	public JTextField getTxtP1()
	{
		return txtP1;
	}

	/**
	 * @return the txtP2
	 */
	public JTextField getTxtP2()
	{
		return txtP2;
	}

	/**
	 * @param lblP1 the lblP1 to set
	 */
	public void setLblP1(JLabel lblP1)
	{
		this.lblP1 = lblP1;
	}

	/**
	 * @param lblP2 the lblP2 to set
	 */
	public void setLblP2(JLabel lblP2)
	{
		this.lblP2 = lblP2;
	}

	/**
	 * @param txtP1 the txtP1 to set
	 */
	public void setTxtP1(JTextField txtP1)
	{
		this.txtP1 = txtP1;
	}

	/**
	 * @param txtP2 the txtP2 to set
	 */
	public void setTxtP2(JTextField txtP2)
	{
		this.txtP2 = txtP2;
	}
}
