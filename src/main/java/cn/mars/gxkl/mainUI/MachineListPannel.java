package cn.mars.gxkl.mainUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;

import cn.mars.gxkl.controller.MachineInfoController;
import cn.mars.gxkl.controller.dataobject.MachineInfoProperty;

public class MachineListPannel extends JPanel implements MachineInfoController {

	private JLabel[] btn = new JLabel[100];
	private GridBagConstraints gbc_btn[] = new GridBagConstraints[100];
	private JPanel panel;
	private SoftBevelBorder border = new SoftBevelBorder(1);

	private int findAPosition() {
		for (int i = 0; i < 100; i++) {
			if (btn[i] == null) {
				return i;
			}
		}
		return (int) (Math.random() * 100);
	}

	public MachineListPannel() {
		setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWeights = new double[] { 1 };
		gbl_panel.rowWeights = new double[] { 0.0 };
		panel.setLayout(gbl_panel);

		// JLabel lblNewLabel_1 = new JLabel("New label");
		// GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		// gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		// gbc_lblNewLabel_1.gridx = 0;
		// gbc_lblNewLabel_1.gridy = 0;
		// panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		// lblNewLabel_1.setBorder(border);
		//
		// JLabel lblNewLabel_2 = new JLabel("New label");
		// GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		// gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		// gbc_lblNewLabel_2.gridx = 0;
		// gbc_lblNewLabel_2.gridy = 1;
		// panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		// lblNewLabel_2.setBorder(border);
		//
		// JLabel lblNewLabel = new JLabel("New label");
		// GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		// gbc_lblNewLabel.gridx = 0;
		// gbc_lblNewLabel.gridy = 2;
		// panel.add(lblNewLabel, gbc_lblNewLabel);
		// lblNewLabel.setBorder(border);

		// JLabel[] btn=new JLabel[100];
		// GridBagConstraints gbc_btn[]=new GridBagConstraints[100];
		// for (int i=0;i<100;i++){
		// btn[i] = new JLabel("New button");
		// gbc_btn[i] = new GridBagConstraints();
		// gbc_btn[i].insets = new Insets(0, 0, 2, 0);
		// gbc_btn[i].fill=GridBagConstraints.BOTH;
		// gbc_btn[i] .gridx = 0;
		// gbc_btn[i] .gridy = 3+i;
		// panel.add(btn[i], gbc_btn[i]);
		// btn[i].setBorder(border);
		// }

	}

	@Override
	public int addMachine(MachineInfoProperty property) {
		int index = findAPosition();
		btn[index] = new JLabel("New button");
		gbc_btn[index] = new GridBagConstraints();
		gbc_btn[index].insets = new Insets(0, 0, 2, 0);
		gbc_btn[index].fill = GridBagConstraints.BOTH;
		gbc_btn[index].gridx = 0;
		gbc_btn[index].gridy = index;
		panel.add(btn[index], gbc_btn[index]);
		btn[index].setBorder(border);
		return 0;
	}

	@Override
	public void changeInfo(MachineInfoProperty property) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMachine(int index) {
		// TODO Auto-generated method stub

	}

}
