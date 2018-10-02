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

import com.mpc.iso.ISOMux;
import com.mpc.iso.tcpip.Channel;
import com.mpc.iso.utils.IOFile;
import com.mpc.model.Configuration;
import com.mpc.model.HeaderConfig;
import com.mpc.model.HeaderConfig.HEADER_TYPE;
import com.mpc.service.ConnectionService;
import com.mpc.service.iConnection;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

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
	
	private JTextField txHeaderStartValue;
	private JTextField txHeaderMidValue;
	private JTextField txHeaderEndValue;
	private JTextField txHeaderLength;
	private JComboBox listbxPackager;
	private JComboBox listbxHeaderType;
	private JCheckBox cbCustomHeader;
	private JPanel panelCustomHeader;
	
/***
 * 
 */
	public static final String TAG_DEFAULT = "-- default --";
	private ISOMux mux = null;
	private iConnection connectionService;
	private Channel channel;
	
	/**
	 * Launch the application.
	 * @throws ISOException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ISOException {
		final Main window = new Main();
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
		frmVsim.setFont(new Font("Arial", Font.PLAIN, 11));
		frmVsim.setTitle("VSim");
		frmVsim.setBounds(100, 100, 800, 600);
		frmVsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVsim.setLocationRelativeTo(null);
		
		JScrollPane spLogging = new JScrollPane();
		spLogging.setAlignmentX(2.0f);
		spLogging.setBounds(116, 57, 380, 278);
		
		taLogging = new JTextArea();
		taLogging.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		taLogging.setFont(new Font("SansSerif", Font.PLAIN, 12));
		taLogging.setEditable(false);
		spLogging.setViewportView(taLogging);
		taLogging.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		taLogging.setBorder(new EmptyBorder(0, 0, 0, 0));
		taLogging.setAutoscrolls(true);
		
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
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(cbWrapLine)
						.addComponent(spLogging, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE))
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
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panel.setLayout(null);
		
		txIP = new JTextField();
		txIP.setFont(new Font("Dialog", Font.PLAIN, 11));
		txIP.setBounds(66, 23, 103, 20);
		panel.add(txIP);
		txIP.setText("localhost");
		txIP.setColumns(10);
		
		txPort = new JTextField();
		txPort.setFont(new Font("Dialog", Font.PLAIN, 11));
		txPort.setBounds(66, 49, 103, 20);
		panel.add(txPort);
		txPort.setText("8888");
		txPort.setColumns(10);
		
		cbMode = new JCheckBox("Client mode");
		cbMode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbMode.setFont(new Font("Dialog", Font.PLAIN, 11));
		cbMode.setBounds(175, 22, 91, 23);
		cbMode.addChangeListener(this);
		panel.add(cbMode);
		
		JLabel lblIp = new JLabel("IP");
		lblIp.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblIp.setBounds(14, 26, 31, 14);
		panel.add(lblIp);
		
		lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblPort.setBounds(14, 52, 31, 14);
		panel.add(lblPort);
		
		listbxPackager = new JComboBox();
		listbxPackager.setFont(new Font("Tahoma", Font.PLAIN, 11));
		listbxPackager.setBounds(66, 75, 128, 20);
		panel.add(listbxPackager);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Header", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 106, 252, 162);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblHeaderType = new JLabel("Type :");
		lblHeaderType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblHeaderType.setBounds(10, 26, 42, 14);
		panel_1.add(lblHeaderType);
		
		listbxHeaderType = new JComboBox();
		listbxHeaderType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		listbxHeaderType.setBounds(52, 23, 131, 20);
		panel_1.add(listbxHeaderType);
		
		cbCustomHeader = new JCheckBox("Custom header");
		cbCustomHeader.setFont(new Font("Arial", Font.PLAIN, 11));
		cbCustomHeader.setBounds(7, 51, 107, 23);
		cbCustomHeader.addChangeListener(this);
		panel_1.add(cbCustomHeader);
		
		panelCustomHeader = new JPanel();
		panelCustomHeader.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCustomHeader.setBounds(2, 62, 248, 100);
		panel_1.add(panelCustomHeader);
		panelCustomHeader.setLayout(null);
		
		JLabel lblHeaderLength = new JLabel("Header length:");
		lblHeaderLength.setBounds(10, 14, 86, 14);
		panelCustomHeader.add(lblHeaderLength);
		lblHeaderLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		txHeaderLength = new JTextField();
		txHeaderLength.setBounds(154, 11, 44, 20);
		panelCustomHeader.add(txHeaderLength);
		txHeaderLength.setToolTipText("Header length");
		txHeaderLength.setColumns(10);
		
		txHeaderStartValue = new JTextField();
		txHeaderStartValue.setBounds(154, 32, 86, 20);
		panelCustomHeader.add(txHeaderStartValue);
		txHeaderStartValue.setColumns(10);
		
		txHeaderMidValue = new JTextField();
		txHeaderMidValue.setBounds(154, 53, 86, 20);
		panelCustomHeader.add(txHeaderMidValue);
		txHeaderMidValue.setColumns(10);
		
		txHeaderEndValue = new JTextField();
		txHeaderEndValue.setBounds(154, 74, 86, 20);
		panelCustomHeader.add(txHeaderEndValue);
		txHeaderEndValue.setColumns(10);
		
		JLabel lblEndValue = new JLabel("End value");
		lblEndValue.setBounds(10, 77, 98, 14);
		panelCustomHeader.add(lblEndValue);
		lblEndValue.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblMidValue = new JLabel("Mid value");
		lblMidValue.setBounds(10, 56, 98, 14);
		panelCustomHeader.add(lblMidValue);
		lblMidValue.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblStartHeaderValue = new JLabel("Start value");
		lblStartHeaderValue.setBounds(10, 35, 98, 14);
		panelCustomHeader.add(lblStartHeaderValue);
		lblStartHeaderValue.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblPackager = new JLabel("Packager");
		lblPackager.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPackager.setBounds(14, 78, 46, 14);
		panel.add(lblPackager);
		
		btStart = new JToggleButton("START");
		btStart.setBounds(167, 279, 86, 23);
		panel.add(btStart);
		btStart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btStart.setFont(new Font("Dialog", Font.PLAIN, 11));
		btStart.addActionListener(this);
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
				listbxPackager.addItem(TAG_DEFAULT);
				for(File f : files)
					listbxPackager.addItem(f.getName());
				
				/***
				 * List header type
				 */
				listbxHeaderType.addItem("Ascii");
				listbxHeaderType.addItem("Hex");
				
				/***
				 * Disable custom header : default
				 */
				setEnableCustomHeader(false);
				
				/***
				 * Set text area logger
				 */
				UILogAppenderListener.setTextArea(taLogging);
				
				/***
				 * Create objek
				 */
				connectionService = new ConnectionService();
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
				if(checkBox.isSelected()) {
					checkBox.setText("Server mode");
					txIP.setEnabled(false);
				}else {
					checkBox.setText("Client mode");
					txIP.setEnabled(true);
				}
			}else if(checkBox.equals(cbCustomHeader)) {
				setEnableCustomHeader(checkBox.isSelected());
			}
		}
	}
	
	private void setEnableCustomHeader(boolean isSelected) {
		txHeaderLength.setEnabled(isSelected);
		txHeaderStartValue.setEnabled(isSelected);
		txHeaderMidValue.setEnabled(isSelected);
		txHeaderEndValue.setEnabled(isSelected);		
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
					Configuration config = null;
					try {
						config = getConfiguration();
					} catch (ISOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					mux = connectionService.start(config);
				}else {
					System.out.println(channel + " close");
					if(mux != null) {
						connectionService.stop(mux);
					}
				}
			}
		}
	}
	
	public Configuration getConfiguration() throws ISOException {
		Configuration config = new Configuration();
		HeaderConfig header = new HeaderConfig();
		
		if(cbMode.isSelected()) {
			config.setIp("");
		}else {
			config.setIp(txIP.getText());
		}
		
		header.setHeaderType(HEADER_TYPE.ASCII);
		config.setPort(Integer.parseInt(txPort.getText()));
		config.setCustomPackager(listbxPackager.getSelectedItem().toString().replace(TAG_DEFAULT, ""));
		config.setHeaderConfig(header);
		return config;
	}
}
