package com.mpc.form;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.jpos.iso.ISOException;
import org.jpos.iso.packager.ISO87APackager;

import com.mpc.iso.tcpip.AsciiChannel;
import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.tcpip.ChannelFactory;

public class Main{

	private JFrame frame;
	JTextArea taLogging;
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws ISOException 
	 */
	public static void main(String[] args) throws IOException, ISOException {
		final Main window = new Main();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		window.initIso();
	}

	public void initIso() throws IOException, ISOException {
		LogAppender.setTextArea(taLogging);
		Channel channel = AsciiChannel.getInstantce().Server(
				"ServerChannel", 
				9899, 
				new ISO87APackager(), 
				false);
		ChannelFactory.createInstance(channel);
	}
	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 541, 352);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		taLogging = new JTextArea();
		frame.getContentPane().add(taLogging, "name_565848441268818");
	}
}
