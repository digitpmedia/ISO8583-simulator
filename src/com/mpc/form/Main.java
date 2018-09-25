package com.mpc.form;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.ISO87APackager;

import com.mpc.iso.ISOMux;
import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.tcpip.ChannelFactory;
import com.mpc.iso.tcpip.HeaderConfiguration;
import com.mpc.iso.tcpip.HexChannel;

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
		Channel channel = HexChannel.Builder().Server(
				"ServerChannel", 
				8899, 
				new ISO87APackager(), 
				false);
		channel.setHeaderConfiguration(new HeaderConfiguration() {
			@Override
			public byte[] sendMessageLength(int len) throws IOException {
				if (len > 0xFFFF)
					throw new IOException (len + " exceeds maximum length");
				byte b[] = new byte[2];
				b[0] = (byte) (len);
				b[1] = (byte) (len>>8);
				return String.format("ISOMPNGEN2%c%c", b[0],b[1]).getBytes();
			}

			@Override
			public int getMessageLength(DataInputStream serverIn) throws IOException, ISOException {
				byte[] b = new byte[12];
				serverIn.readFully(b,0,12);
				return serverIn.available();
			}
		});
		
		ISOMux mux = ChannelFactory.createInstance(channel);
		
		/*ISOMsg iso = new ISOMsg();
		iso.setMTI("0800");
		iso.set(7,"0925231412");
		iso.set(11,"123456");
		iso.set(32,"627820");
		iso.set(70,"001");
		mux.send(iso);*/
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
		taLogging.setAutoscrolls(true);
	}
}
