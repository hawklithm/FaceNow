package cn.mars.gxkl.mainUI;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;

import org.hawklithm.center.InfoCenter;

public class UserMainFace {

	private JFrame frame;
	private InfoCenter infoCenter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserMainFace window = new UserMainFace();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UserMainFace() {
		infoCenter=new InfoCenter();
		initialize(infoCenter);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(InfoCenter infoCenter) {
		frame = new JFrame();
		frame.setBounds(100, 100, 721, 537);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{0.2,0.8,0.2};
		gridBagLayout.rowWeights = new double[]{0.6,0.4};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		Detail detailPannel = new Detail();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.fill = GridBagConstraints.BOTH;
		gbc_comboBox_1.gridheight=1;
		gbc_comboBox_1.gridwidth=1;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 0;
		frame.getContentPane().add(detailPannel, gbc_comboBox_1);
		
		WorkerInfoPannel workerPannel = new WorkerInfoPannel();
		GridBagConstraints gbc_workerPannel = new GridBagConstraints();
		gbc_workerPannel.insets = new Insets(5, 5, 5, 5);
		gbc_workerPannel.fill = GridBagConstraints.BOTH;
		gbc_workerPannel.gridheight=2;
		gbc_workerPannel.gridwidth=1;
		gbc_workerPannel.gridx = 0;
		gbc_workerPannel.gridy = 0;
		frame.getContentPane().add(workerPannel, gbc_workerPannel);
		
		RealTimeDetail realTimeDetail = new RealTimeDetail(2,"工段","车间");
		GridBagConstraints gbc_realTimeDetail = new GridBagConstraints();
		gbc_realTimeDetail.insets = new Insets(5, 5, 5, 5);
		gbc_realTimeDetail.fill = GridBagConstraints.BOTH;
		gbc_realTimeDetail.gridx = 1;
		gbc_realTimeDetail.gridy = 1;
		gbc_realTimeDetail.gridwidth=1;
		frame.getContentPane().add(realTimeDetail, gbc_realTimeDetail);
		
		
		
		MachineListPannel machineList = new MachineListPannel();
		GridBagConstraints gbc_machineList = new GridBagConstraints();
		gbc_machineList.insets = new Insets(5, 5, 5, 5);
		gbc_machineList.fill = GridBagConstraints.BOTH;
		gbc_machineList.gridx = 2;
		gbc_machineList.gridy = 0;
		gbc_machineList.gridheight=2;
		frame.getContentPane().add(machineList, gbc_machineList);
		
		infoCenter.setDetailController(detailPannel);
		infoCenter.setMachineInfoController(machineList);
		infoCenter.setRealTimeTabController(realTimeDetail);
		infoCenter.setWorkerBaseInfoController(workerPannel);
		
	}

}
