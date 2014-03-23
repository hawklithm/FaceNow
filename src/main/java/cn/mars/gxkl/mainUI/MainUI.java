package cn.mars.gxkl.mainUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.MatteBorder;

import cn.mars.gxkl.netty.MessageManage;

public class MainUI extends JFrame {

	private static final long serialVersionUID = 8228895385333157445L;
	private int width,height,topNavHeight,topNavWidth,mainHeight,mainWidth;
	private JPanel topNav,main;
	private JFrame now;
	private LineWindowPanel[] lineWindowPanel;
	private MessageManage messageManager;
	
	public MainUI(int width, int height) {
		now = this;
		this.width = width;
		this.height = height;
		initHeightAndWidth();
		lineWindowPanel = new LineWindowPanel[10];
		initialization();
		messageManager = new MessageManage(lineWindowPanel,Constant.processName[0],now);
		while(!messageManager.getACK()) {
			if(!messageManager.getConnectionStatus()) {
				JOptionPane.showMessageDialog(null, "连接服务器失败","错误",JOptionPane.ERROR_MESSAGE);
				continue;
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(messageManager.getACK()) {
			new Thread(messageManager).start();
		}
	}
	
	private void initHeightAndWidth() {
		topNavHeight = (int)(height*0.07);
		topNavWidth = width;
		mainHeight = (int)(height*0.93);
		mainWidth = width;
	}
	
	private void initialization() {
		topNav = new JPanel();
		main = new JPanel();
		main = new JPanel();
		
		initPanel(topNav, topNavWidth, topNavHeight, Constant.red);
		initTopNav();
		initPanel(main, mainWidth, mainHeight, Color.white);
		main.add(initMain(Constant.topNavItemNames[0]));
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		this.getContentPane().add(topNav);
		this.getContentPane().add(main);
	}
	
	private void initTopNav() {
		topNav.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		ImageIcon logoIcon = new ImageIcon("logo3.jpg");
		logoIcon.setImage(logoIcon.getImage().getScaledInstance((int)(width*0.12),topNavHeight, Image.SCALE_SMOOTH));
		JLabel logo = new JLabel(logoIcon);
		logo.setPreferredSize(new Dimension((int)(width*0.12),topNavHeight));
		
		JPanel navBar = new JPanel();
		navBar.setPreferredSize(new Dimension(topNavWidth-(int)(width*0.12),topNavHeight));
		navBar.setLayout(new FlowLayout(FlowLayout.LEFT,8,0));
		navBar.setOpaque(false);
		JLabel[] navBar_items = new JLabel[Constant.topNavItemNames.length];
		for(int i=0;i<navBar_items.length;i++) {
			navBar_items[i] = new JLabel(Constant.topNavItemNames[i],JLabel.CENTER);
			navBar_items[i].setPreferredSize(new Dimension((int)(topNavWidth*0.85-10)/(Constant.topNavItemNames.length+4),topNavHeight));
			navBar_items[i].setFont(new Font("雅黑",Font.BOLD,(int)(width*0.015)));
			navBar_items[i].setOpaque(false);
			navBar_items[i].setForeground(Color.white);
			navBar_items[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			navBar_items[i].setName(Constant.topNavItemNames[i]);
			navBar_items[i].addMouseListener(new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					String name = ((JLabel)e.getSource()).getName();
//					System.out.println(main.getComponent(0).getName());
					((JLabel)main.getComponent(0)).setText(name);
					JPanel lineGroup = (JPanel)((JScrollPane)main.getComponent(1)).getViewport().getComponent(0);
					int lineCnt = lineGroup.getComponentCount();
//					System.out.println(lineCnt);
					String lineName = null;
					for(int i=0;i<Constant.topNavItemNames.length;i++) {
						if(Constant.topNavItemNames[i]==name) {
							lineName = Constant.lineTitle[i];
							messageManager.setProcessNow(Constant.processName[i]);
						}
					}
					for(int i=0;i<lineCnt;i++) {
						LineWindowPanel line = (LineWindowPanel)lineGroup.getComponent(i);
						line.setTitle(lineName+" "+(i+1)+"号");
						line.clear();
					}
					messageManager.request();
					while(!messageManager.getACK()) {
						if(!messageManager.getConnectionStatus()) {
							JOptionPane.showMessageDialog(null, "连接服务器失败","错误",JOptionPane.ERROR_MESSAGE);
							continue;
						}
						try {
							Thread.sleep(20);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
			});
			navBar.add(navBar_items[i]);
		}
		
		/***
		 *
		 * 补用户，注销
		 * 
		 ***/
		
		topNav.add(logo);
		topNav.add(navBar);
	}
	
	private JPanel initMain(String header) {
		main = new JPanel();
		initPanel(main, mainWidth, mainHeight, Color.white);
		main.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
		
		JLabel title = new JLabel(header);
		title.setName("title");
		title.setPreferredSize(new Dimension((int)(mainWidth*0.98),(int)(mainHeight*0.05)));
		title.setOpaque(false);
		title.setFont(new Font("雅黑",Font.BOLD,(int)(mainWidth*0.02)));
		title.setForeground(Constant.red);
		title.setBorder(new MatteBorder(0,0,2,0,Constant.red));
		
		JPanel lineGroup = new JPanel();
		lineGroup.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
		lineGroup.setOpaque(false);
		lineGroup.setPreferredSize(new Dimension((int)(mainWidth*0.98),(int)(mainHeight*0.45+5)*3+3));
		lineGroup.setName("lineGroup");
		for(int i=0;i<lineWindowPanel.length;i++) {
			lineWindowPanel[i] = new LineWindowPanel((int)(mainWidth*0.245-12),(int)(mainHeight*0.45),Constant.lineTitle[0]+" "+(i+1)+"号","");
			lineGroup.add(lineWindowPanel[i]);
		}
		
		JScrollPane scrollingGroup = new JScrollPane(lineGroup);
		scrollingGroup.setPreferredSize(new Dimension((int)(mainWidth*0.98)+17,(int)(mainHeight*0.91)));
		scrollingGroup.getViewport().setOpaque(false);
		scrollingGroup.setBorder(null);
		scrollingGroup.setOpaque(false);
		
		JScrollBar scrollBar = scrollingGroup.getVerticalScrollBar();
		scrollBar.setOpaque(false);
		scrollBar.setBorder(null);
		
		main.add(title);
		main.add(scrollingGroup);
		return main;
	}
	
	private void initPanel(JPanel p,int width,int height,Color color) {
		p.setBackground(color);
		p.setPreferredSize(new Dimension(width,height));
	}
	
}