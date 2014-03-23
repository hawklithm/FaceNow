package cn.mars.gxkl.mainUI;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;

public class MainProgram {
	public static void main(String[] args) {
//		test();
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		MainUI frame = new MainUI(width, height);
		frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
        frame.setSize(width, height);
        frame.setVisible(true);
	}
	
	private static void test() {
		try {
			Process process = Runtime.getRuntime().exec("which google-chrome");
			System.out.println(process.waitFor());
			InputStream inputStream = process.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
			String s;
			while((s = bf.readLine()) != null) {
				System.out.println(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
