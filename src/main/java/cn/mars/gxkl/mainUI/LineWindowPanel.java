package cn.mars.gxkl.mainUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cn.mars.gxkl.utils.LinkedList;

public class LineWindowPanel extends JPanel {

	private static final long serialVersionUID = -5325295920795564648L;
	private int width,height;
	private JTextArea content;
	private JPanel header;
	private JLabel title,history;
	private String name,url;
	private LinkedList<String> info;
	
	public LineWindowPanel(int width,int height,String name,String url) {
		this.width = width;
		this.height = height;
		this.name = name;
		this.url = url;
		info = new LinkedList<String>(18);
		this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Constant.lightGray);
		initialization();
	}
	
	private void initialization() {
//		System.out.println(width+","+height);
		header = new JPanel();
		header.setOpaque(true);
		header.setBackground(Constant.red);
		header.setPreferredSize(new Dimension(width,(int)(height*0.06)));
		header.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		
		title = new JLabel(name);
		title.setForeground(Color.white);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension((int)(width*0.8),(int)(height*0.06)));
		title.setFont(new Font("雅黑",Font.PLAIN,(int)(height*0.04)));
		title.setOpaque(false);
		
		history = new JLabel("历史纪录");
		history.setForeground(Color.white);
		history.setVerticalAlignment(JLabel.CENTER);
		history.setPreferredSize(new Dimension((int)(width*0.2),(int)(height*0.06)));
		history.setFont(new Font("雅黑",Font.ITALIC,(int)(height*0.04)));
		history.setOpaque(false);
		history.setCursor(new Cursor(Cursor.HAND_CURSOR));
		history.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					openURL();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		
		content = new JTextArea(20,25);
		content.setFont(new Font("雅黑",Font.PLAIN,(int)(height*0.04)));
		content.setEditable(false);
		content.setForeground(Color.black);
		content.setOpaque(false);
		content.setLineWrap(true);
//		for(int i=0;i<18;i++) {
//			Date now = new Date();
//			DateFormat format = DateFormat.getTimeInstance();
//		    String str = format.format(now);
//			content.append(" ["+str+"]:"+"一把锤子进入入入入入入入入入\r\n");
//		}
		
		header.add(title);
		header.add(history);
		
		this.add(header);
		this.add(content);
	}
	
	private void openURL() throws InterruptedException, IOException {
		boolean opened = false;
		String osName = System.getProperty("os.name", "");
		if(osName.startsWith(Constant.osNames[0])) {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+url);
			opened = true;
		}
		else if(osName.startsWith(Constant.osNames[1])) {
			/***
			 * 
			 * 添加MAC OS下打开浏览器代码。
			 * 
			 ***/
		}
		else {
			for(int i = 0;i<Constant.browsers.length;i++) {
				if(Runtime.getRuntime().exec("which "+Constant.browsers[i]).waitFor() == 0) {
					Runtime.getRuntime().exec(Constant.browsers[i]+" "+url);
					opened = true;
					break;
				}
			}
		}
		if(!opened) {
			JOptionPane.showMessageDialog(null, "找不到浏览器，请检查后重试。","错误",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setRFID(String rfid) {
		title.setText(title.getText()+"\tRFID:"+rfid);
	}
	
	public void setTitle(String name) {
		title.setText(name);
	}
	
	public void setURL(String url) {
		this.url = url;
	}
	
	public void append(String info) {
		this.info.append(info);
		this.content.setText(this.info.getContent());
	}
	
	public void clear() {
		this.content.setText("");
		this.info.clear();
	}
}
