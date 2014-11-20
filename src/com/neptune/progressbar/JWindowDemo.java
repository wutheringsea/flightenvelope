package com.neptune.progressbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class JWindowDemo extends JWindow implements Runnable {
	Thread splashThread; // �����������߳�

	JProgressBar progress; // ������

	public JWindowDemo() {
	   Container container = getContentPane(); // �õ�����
	   setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // ���ù��Ϊ�ȴ�״̬
	   URL url = getClass().getResource("d://eclipse.zip");

	   if (url != null) {
	    container.add(new JLabel(new ImageIcon(url)), BorderLayout.CENTER);
	   }
	   progress = new JProgressBar(1, 100); // ʵ����������
	   progress.setStringPainted(true); // �������
	   progress.setString("���������,���Ժ�......"); // ������ʾ����
	   progress.setBackground(Color.white); // ���ñ���ɫ
	   container.add(progress, BorderLayout.SOUTH); // ���ӽ�������������
	   Dimension screen = getToolkit().getScreenSize(); // �õ���Ļ�ߴ�
	   pack(); // ������Ӧ����ߴ�
	   setLocation((screen.width - getSize().width) / 2,
	     (screen.height - getSize().height) / 2); // ���ô���λ��
	}

	public void start() {
	   this.toFront(); // ����ǰ����ʾ
	   splashThread = new Thread(this); // ʵ�����߳�
	   splashThread.start(); // ��ʼ�����߳�
	}

	public void run() {
	   setVisible(true); // ��ʾ����
	   try {
	    for (int i = 0; i < 100; i++) {
	     Thread.sleep(300); // �߳�����
	     progress.setValue(progress.getValue() + 1); // �����߳̽��У����ӽ�����ֵ
	    }
	   } catch (Exception ex) {
	    ex.printStackTrace();
	   }
	   dispose(); // �ͷŴ���
	   showFrame(); 
	}

	static void showFrame() {
	   JFrame frame = new JFrame("��������������ʾ");

	   frame.setSize(300, 200);

	   frame.setVisible(true); // ���ڿ���
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �رմ���ʱ�˳�����
	}

	public static void main(String[] args) {
	   JWindowDemo splash = new JWindowDemo();
	   splash.start(); // ������������
	}
	}
