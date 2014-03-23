package cn.mars.gxkl.mainUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EmergencyWindow extends JDialog {
	
	private static final long serialVersionUID = 8378232376975890898L;
	private int width,height;
	private List<String> cache;
	private int pageNow,maxPage;
	private JTextArea content;
	private boolean isShow;
	
	public EmergencyWindow(int width,int height,JFrame frame) {
		super(frame);
//		super();
		pageNow = 0;
		maxPage = 0;
		this.width = width;
		this.height = height;
		cache = new ArrayList<String>();
		isShow = false;
		System.out.println("aa");
//		int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-width-10;
//		int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()-height-10;
//		//this.setPreferredSize(new Dimension(width,height));
//		this.setSize(width, height);
//		this.setLocation(x, y+height);
//		this.setBounds(x, y, width, height);
		this.getContentPane().setBackground(Constant.red);
		initialization();
//		this.setModal(true);
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.setResizable(false);
		this.getContentPane().setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
	}
	
	private void initialization() {
		  JPanel[] pane = new JPanel[3];
		  for(int i=0;i<pane.length;i++) {
			  pane[i] = new JPanel();
			  pane[i].setOpaque(false);
		  }
		  pane[0].setPreferredSize(new Dimension(width,(int)(height*0.15)));
		  pane[1].setPreferredSize(new Dimension(width,(int)(height*0.75)));
		  pane[2].setPreferredSize(new Dimension(width,(int)(height*0.1)));
		  
		  JLabel closeBtn = new JLabel();
		  closeBtn.setPreferredSize(new Dimension((int)(width*0.1),(int)(height*0.15)));
		  closeBtn.setOpaque(true);
		  closeBtn.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				try {
					close();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		  });
		  pane[0].setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
		  pane[0].add(closeBtn);
		  
		  content = new JTextArea(9,27);
		  int fontSize = (int)(width*0.04);
		  content.setFont(new Font("雅黑",Font.PLAIN,fontSize));
		  content.setLineWrap(true);
		  content.setOpaque(false);
		  content.setEditable(false);
		  content.setForeground(Color.white);
		  content.setPreferredSize(new Dimension(fontSize*24,(int)(height*0.15-10)));
//		  content.setText("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
//				  +"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
//				  +"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
//				  +"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊"
//				  +"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
		  pane[1].setLayout(new FlowLayout(FlowLayout.CENTER,10,5));
		  pane[1].add(content);
		  
		  MouseListener changePage = new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					JLabel btn = (JLabel)e.getSource();
					int dx = -1;
					if(btn.getName().equals("next")) {
						dx = 1;
					}
					pageNow += dx;
					if(pageNow<0)
						pageNow = 0;
					if(pageNow>=maxPage)
						pageNow = maxPage-1;
					String info = cache.get(pageNow);
					content.setText(info);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					JLabel btn = (JLabel)e.getSource();
					btn.setForeground(Color.black);
					btn.setBackground(Color.white);
					btn.setOpaque(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					JLabel btn = (JLabel)e.getSource();
					btn.setForeground(Color.white);
					btn.setOpaque(false);
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
			  };
		  
		  JLabel next = new JLabel(">",JLabel.CENTER);
		  JLabel pre = new JLabel("<",JLabel.CENTER);
		  next.setPreferredSize(new Dimension((int)(width*0.1),(int)(height*0.1-4)));
		  next.setOpaque(false);
		  next.setFont(new Font("雅黑",Font.BOLD,18));
		  next.setForeground(Color.white);
		  next.setName("next");
		  next.addMouseListener(changePage);
		  pre.setPreferredSize(new Dimension((int)(width*0.1),(int)(height*0.1-4)));
		  pre.setOpaque(false);
		  pre.setName("pre");
		  pre.setForeground(Color.white);
		  pre.setFont(new Font("雅黑",Font.BOLD,18));
		  pre.addMouseListener(changePage);
		  pane[2].setLayout(new FlowLayout(FlowLayout.RIGHT,5,0));
		  pane[2].add(pre);
		  pane[2].add(next);
		  
		  for(int i=0;i<pane.length;i++) {
			  this.getContentPane().add(pane[i]);
		  }
		  
	}
	
	public void open() {
		this.setVisible(true);
		int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		for(int i=0;i<=height;i+=10) {
			this.setBounds(x-width-10, y-i-10, width, i);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isShow = true;
		pageNow = 0;
		content.setText(cache.get(pageNow));
	}
	
	public void close() throws InterruptedException{
		int x = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		for(int i=height;i>=0;i-=10) {
			this.setBounds(x-width-10, y-i-10, width, i);
			Thread.sleep(20);
		}
		this.setVisible(false);
		isShow = false;
		cache.clear();
		pageNow = 0;
		maxPage = 0;
	}
	
	public void append(String info) {
		cache.add(info);
		maxPage++;
	}
	
	public void append(List<String> info) {
		cache.addAll(info);
		maxPage += info.size();
	}
	
	public boolean isShow() {
		return isShow;
	}
}
