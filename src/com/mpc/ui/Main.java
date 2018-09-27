package com.mpc.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

import com.mpc.iso.ISOMux;
import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.tcpip.ChannelFactory;
import com.mpc.iso.tcpip.DJPHeaderConfigListener;
import com.mpc.iso.tcpip.HexChannel;

public class Main{

	private JFrame frame;
	JTextArea taLogging;
	
	/***
	 * ISO
	 */
	ISOMux mux;
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
		GenericPackager packager = null;
		try {
			packager = new GenericPackager("config/djpIso.xml");
		} catch (ISOException e) {
			e.printStackTrace();
		}
		
		UILogAppenderListener.setTextArea(taLogging);
		Channel channel = HexChannel.Builder().Client(
				"DJPServerChannel", 
				"10.10.77.42",
				20002,
				packager, 
				false);
		channel.setHeaderConfiguration(new DJPHeaderConfigListener());
		mux = ChannelFactory.createInstance(channel);
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
		frame.setBounds(100, 100, 516, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ISOMsg iso = new ISOMsg();
				try {
					iso.setMTI("0800");
					iso.set(7,"0925231412");
					iso.set(11,"123456");
					iso.set(32,"627820");
					iso.set(70,"001");
					mux.send(iso);
				} catch (ISOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		taLogging = new JTextArea();
		taLogging.setAutoscrolls(true);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addComponent(btnSend)
					.addGap(53)
					.addComponent(taLogging, GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSend)
						.addComponent(taLogging, GroupLayout.PREFERRED_SIZE, 601, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(335, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
