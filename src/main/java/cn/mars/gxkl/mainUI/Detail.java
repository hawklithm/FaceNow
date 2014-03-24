package cn.mars.gxkl.mainUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.mars.gxkl.controller.DetailContoller;
import cn.mars.gxkl.controller.dataobject.ObjectPair;

public class Detail extends JPanel implements DetailContoller{

	private List<ArrayList<ObjectPair>> objects;
	private JLabel lblNewLabel;
	private JComboBox comboBox;
	JComboBox comboBox_1 = new JComboBox();
	private JLabel lblNewLabel_1;
	private JTextArea textArea;
	JLabel lblNewLabel_2 = new JLabel("手术钳");
	JComboBox comboBox_2 = new JComboBox();
	
	public Detail() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.1,0.4,1.0};
		gridBagLayout.rowWeights = new double[]{0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1,0.1, 0.1};
		setLayout(gridBagLayout);
		
		 lblNewLabel = new JLabel("手术刀");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		 comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBox, gbc_comboBox);
		
		 lblNewLabel_1 = new JLabel("锤子");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		 comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 1;
		add(comboBox_1, gbc_comboBox_1);
		
		 lblNewLabel_2 = new JLabel("手术钳");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		 comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 2;
		add(comboBox_2, gbc_comboBox_2);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		 textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		initBuffer(3);
	}
	
	private void initBuffer(int number){
		objects.clear();
		objects= new ArrayList<ArrayList<ObjectPair>>();
		for (int i=0;i<number;i++){
			objects.add(new ArrayList<ObjectPair>());
		}
	}

	@Override
	synchronized public void addScalpels(ObjectPair... objectPair) {
		List<ObjectPair> pairs=objects.get(0);
		for (int i=0;i<objectPair.length;i++){
			pairs.add(objectPair[i]);
		}
	}
	

	@Override
	synchronized public void addHammers(ObjectPair... objectPair) {
		List<ObjectPair> pairs=objects.get(1);
		for (int i=0;i<objectPair.length;i++){
			pairs.add(objectPair[i]);
		}
		
	}

	@Override
	synchronized public void addForcepses(ObjectPair... objectPair) {
		List<ObjectPair> pairs=objects.get(2);
		for (int i=0;i<objectPair.length;i++){
			pairs.add(objectPair[i]);
		}
		
	}

	@Override
	synchronized public void clearAll() {
		initBuffer(3);
	}

}
