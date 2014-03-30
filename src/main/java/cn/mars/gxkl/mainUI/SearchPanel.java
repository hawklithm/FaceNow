package cn.mars.gxkl.mainUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SearchPanel extends JPanel {
	
	private int width,height;
	
	public SearchPanel(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		initialization();
	}
	
	private void initialization() {
		this.setPreferredSize(new Dimension(width,height));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,5,3));
		this.setOpaque(false);
		
		JTextField input = new JTextField();
		input.setPreferredSize(new Dimension((int)(width*0.9),height/2-5));
		input.setFont(new Font("ÑÅºÚ",Font.PLAIN,(int)(width*0.01)));
		input.setBackground(Color.white);
		input.setBorder(new LineBorder(Color.black));
		input.setFont(new Font("ÑÅºÚ",Font.PLAIN,(int)(width*0.015)));
		this.add(input);
		
		JLabel button = new JLabel("ËÑË÷",JLabel.CENTER);
		button.setPreferredSize(new Dimension((int)(width*0.1-18),height/2-5));
		button.setBackground(new Color(0x92,0,1));
		button.setForeground(Color.white);
		button.setOpaque(true);
		button.setFont(new Font("ÑÅºÚ",Font.BOLD,(int)(width*0.018)));
		this.add(button);
		
		String[] name = {
				"ÎÞ²éÑ¯","¼üÅÌÊäÈë²éÑ¯","RFIDÉ¨Ãè²éÑ¯","ÆäËûÉ¨Ãè²éÑ¯"
		};
		JLabel[] type_items = new JLabel[4];
		for(int i=0;i<type_items.length;i++) {
			type_items[i] = new JLabel(name[i],JLabel.CENTER);
			type_items[i].setBackground(new Color(0x92,0,1));
			type_items[i].setForeground(Color.white);
			type_items[i].setPreferredSize(new Dimension((int)(width/4-7),height/2-5));
			type_items[i].setFont(new Font("ÑÅºÚ",Font.PLAIN,(int)(width*0.015)));
			type_items[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			type_items[i].setOpaque(true);
			this.add(type_items[i]);
		}
	}
}
