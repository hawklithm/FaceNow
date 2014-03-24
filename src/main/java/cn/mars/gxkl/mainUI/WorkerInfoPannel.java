package cn.mars.gxkl.mainUI;

import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import cn.mars.gxkl.controller.WorkerBaseInfoController;

public class WorkerInfoPannel extends JPanel implements WorkerBaseInfoController{
	private JLabel lblNewLabel;
	private JTextArea txtrDfasdfasdf;
	/**
	 * Create the panel.
	 */
	public WorkerInfoPannel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.5,1.0};
		gridBagLayout.rowWeights = new double[]{0.3, 0.7 };
		setLayout(gridBagLayout);
		
		JLabel lblTest = new JLabel("test");
		GridBagConstraints gbc_lblTest = new GridBagConstraints();
		gbc_lblTest.fill = GridBagConstraints.BOTH;
		gbc_lblTest.insets = new Insets(0, 0, 5, 5);
		gbc_lblTest.gridx = 0;
		gbc_lblTest.gridy = 0;
		add(lblTest, gbc_lblTest);
		
		lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridheight = 1;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		 txtrDfasdfasdf = new JTextArea();
		scrollPane.setViewportView(txtrDfasdfasdf);
		txtrDfasdfasdf.setTabSize(4);

	}

	@Override
	public void setWorkerInfo(String message) {
		lblNewLabel.setText(message);
	}

	@Override
	public void setMachineInfo(String message) {
		txtrDfasdfasdf.setText(message);
		
	}

	@Override
	public void setWorkerImage(String url) {
		
	}

}
