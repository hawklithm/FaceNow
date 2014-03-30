package cn.mars.gxkl.mainUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.mars.gxkl.controller.RealTimeTabController;

public class RealTimeDetail extends JPanel implements RealTimeTabController{
	private JTable[] tables;

	/**
	 * Create the panel.
	 */
	public RealTimeDetail(int tableNumber,String... names ) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		add(tabbedPane, gbc_tabbedPane);
		
		String[] headers = { "机器编号", "信息"  };

		DefaultTableModel model = new DefaultTableModel(null, headers) {

		  public boolean isCellEditable(int row, int column) {
		    return false;
		  }
		};

		
		tables = new JTable[tableNumber];
		int nameListLength=names.length;
		for (int i=0;i<tableNumber;i++){
			tables[i]=new JTable(model);
			String name;
			if (i>=nameListLength){
				name="未命名";
			}else {
				name=names[i];
			}
			tabbedPane.addTab(name, null, tables[i], null);
		}
		

	}

	@Override
	public void addInfo(int id,String message, int index) {
			((DefaultTableModel) tables[index].getModel()).addRow(new Object[]{id+"", message});
	}

	@Override
	public void cleanInfo(int index) {
		
		
	}

}
