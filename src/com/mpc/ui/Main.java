package com.mpc.ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMUX;

import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.utils.IOFile;
import com.mpc.model.Configuration;
import com.mpc.model.HeaderConfig;
import com.mpc.service.iConnection;

public class Main implements ChangeListener, ActionListener{
	private JFrame frmVsim;
	private JTextArea taLogging;
	private JCheckBox cbWrapLine; 

	private JToggleButton btStart;
	private JCheckBox cbMode;
	private JTextField txIP;
	private JTextField txPort;
	private JPanel panel;
	private JLabel lblPort;
	
	private JComboBox<String> listbxPackager;
	
	private ISOMUX mux = null;
	private iConnection connectionService;
	private Channel channel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	/**
	 * Launch the application.
	 * @throws ISOException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ISOException {
		Main window = new Main();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window.frmVsim.setVisible(true);
					window.initializeObjectContent();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		frmVsim = new JFrame();
		frmVsim.setTitle("VSim");
		frmVsim.setBounds(100, 100, 800, 600);
		frmVsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVsim.setLocationRelativeTo(null);
		
		JScrollPane spLogging = new JScrollPane();
		spLogging.setAlignmentX(2.0f);
		spLogging.setBounds(116, 57, 380, 278);
		
		taLogging = new JTextArea();
		spLogging.setViewportView(taLogging);
		taLogging.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		taLogging.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		taLogging.setAutoscrolls(true);
		taLogging.setWrapStyleWord(true);
		
		cbWrapLine = new JCheckBox("Line wrap");
		cbWrapLine.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbWrapLine.setFont(new Font("Dialog", Font.PLAIN, 11));
		cbWrapLine.addChangeListener(this);
		cbWrapLine.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		panel = new JPanel();
		panel.setFont(new Font("Dialog", Font.PLAIN, 11));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(frmVsim.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(cbWrapLine)
						.addComponent(spLogging, GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(cbWrapLine)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spLogging, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(25)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel.setLayout(null);
		
		txIP = new JTextField();
		txIP.setFont(new Font("Dialog", Font.PLAIN, 11));
		txIP.setBounds(71, 22, 103, 20);
		panel.add(txIP);
		txIP.setText("localhost");
		txIP.setColumns(10);
		
		txPort = new JTextField();
		txPort.setFont(new Font("Dialog", Font.PLAIN, 11));
		txPort.setBounds(71, 48, 103, 20);
		panel.add(txPort);
		txPort.setText("8888");
		txPort.setColumns(10);
		
		cbMode = new JCheckBox("Client mode");
		cbMode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbMode.setFont(new Font("Dialog", Font.PLAIN, 11));
		cbMode.setBounds(180, 21, 86, 23);
		cbMode.addChangeListener(this);
		panel.add(cbMode);
		
		btStart = new JToggleButton("START");
		btStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btStart.setFont(new Font("Dialog", Font.PLAIN, 11));
		btStart.setBounds(159, 304, 68, 22);
		btStart.addActionListener(this);
		panel.add(btStart);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblIp.setBounds(20, 25, 31, 14);
		panel.add(lblIp);
		
		lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblPort.setBounds(20, 52, 31, 14);
		panel.add(lblPort);
		
		listbxPackager = new JComboBox<String>();
		listbxPackager.setBounds(71, 79, 165, 20);
		panel.add(listbxPackager);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 148, 252, 86);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(156, 11, 86, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(156, 33, 86, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(156, 55, 86, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(10, 55, 86, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		rdbtnNewRadioButton.setBounds(6, 10, 109, 23);
		panel_1.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
		rdbtnNewRadioButton_1.setBounds(6, 32, 109, 23);
		panel_1.add(rdbtnNewRadioButton_1);
		frmVsim.getContentPane().setLayout(groupLayout);
		
		
	}

	private void initializeObjectContent() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				/***
				 * Get list file packager
				 */
				File files[] = IOFile.getFiles(new File("iso/"), null);
				for(File f : files)
					listbxPackager.addItem(f.getName());
				
			}
		}).start();
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		Object obj = e.getSource();
		if(obj instanceof JCheckBox) {
			JCheckBox checkBox = (JCheckBox) obj;
			if(checkBox.equals(cbWrapLine)) {
				taLogging.setLineWrap(checkBox.isSelected());
			}else if(checkBox.equals(cbMode)){
				System.out.println("aaaaa");
				if(checkBox.isSelected()) {
					checkBox.setText("Server mode");
					txIP.setEnabled(false);
				}else {
					checkBox.setText("Client mode");
					txIP.setEnabled(true);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj instanceof JToggleButton) 
		{
			JToggleButton btTogle = (JToggleButton) obj;
			if(btTogle.equals(btStart)) 
			{
				if(btTogle.isSelected()) {
					mux = connectionService.start(channel);
				}else {
					if(channel != null)
						mux = connectionService.stop(channel);
				}
			}
		}
	}
	
	public Configuration getConfiguration() throws ISOException {
		Configuration config = new Configuration();
		if(cbMode.isSelected()) {
			config.setIp("");
		}else {
			config.setIp(txIP.getText());
		}
		config.setPort(Integer.parseInt(txPort.getText()));
		config.setCustomPackager("");
		config.setHeaderConfig(new HeaderConfig());
		
		return config;
	}
}
