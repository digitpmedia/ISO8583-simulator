package com.mpc.ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jpos.iso.ISOException;

import com.mpc.iso.ISOMux;
import com.mpc.iso.model.Configuration;
import com.mpc.iso.model.HEADER_TYPE;
import com.mpc.iso.model.HeaderConfig;
import com.mpc.iso.services.iChannel;
import com.mpc.iso.services.iConnection;
import com.mpc.iso.services.impl.ConnectionService;
import com.mpc.utils.IOFile;

public class Main implements ChangeListener, ActionListener{
	public static final String TAG_DEFAULT = "-- default --";
	private static final String FILE_DATA_RESPONSE = "data/data_response.vsim";
	private ISOMux mux = null;
	private iConnection connectionService;
	private iChannel channel;
	
	/***
	 * Tab Activtys
	 */
	private JFrame frmVsim;
	private JTextArea taLogging;
	private JCheckBox cbWrapLine; 
	private JTextField txIP;
	private JTextField txPort;
	private JTextField txHeaderLength;
	private JTextField txHeaderStartValue;
	private JTextField txHeaderMidValue;
	private JTextField txHeaderEndValue;
	private JToggleButton btStart;
	private JComboBox listbxPackager;
	private JComboBox listbxHeaderType;
	private JCheckBox cbCustomHeader;
	private JCheckBox cbMode;
	private JButton btClear;
	/***
	 * Tab Data
	 */
	/***
	 * Data Resopnse
	 */
	private JTextArea txDataResponse;
	private JButton btSaveResponse;
	
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
		frmVsim.setBounds(100, 100, 812, 619);
		frmVsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVsim.setLocationRelativeTo(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(frmVsim.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 558, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLayeredPane tabActivity = new JLayeredPane();
		tabbedPane.addTab("Activity", null, tabActivity, null);
		tabbedPane.setForegroundAt(0, Color.DARK_GRAY);
		tabbedPane.setBackgroundAt(0, SystemColor.activeCaptionBorder);
		
		JPanel panel_3 = new JPanel();
		GroupLayout gl_tabActivity = new GroupLayout(tabActivity);
		gl_tabActivity.setHorizontalGroup(
			gl_tabActivity.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
		);
		gl_tabActivity.setVerticalGroup(
			gl_tabActivity.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
		);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setFont(new Font("Dialog", Font.PLAIN, 11));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Connection", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		txIP = new JTextField();
		txIP.setText("localhost");
		txIP.setFont(new Font("Dialog", Font.PLAIN, 11));
		txIP.setColumns(10);
		txIP.setBounds(66, 23, 103, 20);
		panel.add(txIP);
		
		txPort = new JTextField();
		txPort.setText("8888");
		txPort.setFont(new Font("Dialog", Font.PLAIN, 11));
		txPort.setColumns(10);
		txPort.setBounds(66, 49, 103, 20);
		panel.add(txPort);
		
		cbMode = new JCheckBox("Client mode");
		cbMode.setFont(new Font("Dialog", Font.PLAIN, 11));
		cbMode.setBounds(175, 22, 91, 23);
		cbMode.addChangeListener(this);
		panel.add(cbMode);
		
		JLabel label = new JLabel("IP");
		label.setFont(new Font("Dialog", Font.PLAIN, 11));
		label.setBounds(14, 26, 31, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Port");
		label_1.setFont(new Font("Dialog", Font.PLAIN, 11));
		label_1.setBounds(14, 52, 31, 14);
		panel.add(label_1);
		
		listbxPackager = new JComboBox();
		listbxPackager.setFont(new Font("Tahoma", Font.PLAIN, 11));
		listbxPackager.setBounds(66, 75, 128, 20);
		panel.add(listbxPackager);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Header", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 106, 252, 151);
		panel.add(panel_1);
		
		JLabel label_2 = new JLabel("Type :");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(10, 26, 42, 14);
		panel_1.add(label_2);
		
		listbxHeaderType = new JComboBox();
		listbxHeaderType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		listbxHeaderType.setBounds(56, 23, 69, 20);
		panel_1.add(listbxHeaderType);
		
		cbCustomHeader = new JCheckBox("Custom header");
		cbCustomHeader.setFont(new Font("Arial", Font.PLAIN, 11));
		cbCustomHeader.setBounds(7, 51, 107, 23);
		cbCustomHeader.addChangeListener(this);
		panel_1.add(cbCustomHeader);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(2, 62, 248, 89);
		panel_1.add(panel_2);
		
		txHeaderStartValue = new JTextField();
		txHeaderStartValue.setColumns(10);
		txHeaderStartValue.setBounds(154, 11, 86, 20);
		panel_2.add(txHeaderStartValue);
		
		txHeaderMidValue = new JTextField();
		txHeaderMidValue.setColumns(10);
		txHeaderMidValue.setBounds(154, 32, 86, 20);
		panel_2.add(txHeaderMidValue);
		
		txHeaderEndValue = new JTextField();
		txHeaderEndValue.setColumns(10);
		txHeaderEndValue.setBounds(154, 53, 86, 20);
		panel_2.add(txHeaderEndValue);
		
		JLabel label_3 = new JLabel("End value");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(10, 56, 98, 14);
		panel_2.add(label_3);
		
		JLabel label_4 = new JLabel("Mid value");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(10, 35, 98, 14);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("Start value");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setBounds(10, 14, 98, 14);
		panel_2.add(label_5);
		
		txHeaderLength = new JTextField();
		txHeaderLength.setToolTipText("Header length");
		txHeaderLength.setColumns(10);
		txHeaderLength.setBounds(198, 23, 44, 20);
		panel_1.add(txHeaderLength);
		
		JLabel label_6 = new JLabel("Length:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_6.setBounds(147, 26, 50, 14);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("Packager");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_7.setBounds(14, 78, 46, 14);
		panel.add(label_7);
		
		btStart = new JToggleButton("START");
		btStart.setFont(new Font("Dialog", Font.PLAIN, 11));
		btStart.setBounds(175, 268, 86, 23);
		btStart.addActionListener(this);
		panel.add(btStart);
		
		JScrollPane spLogging = new JScrollPane();
		spLogging.setAlignmentX(2.0f);
		
		taLogging = new JTextArea();
		taLogging.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		taLogging.setFont(new Font("Monospaced", Font.PLAIN, 12));
		taLogging.setEditable(false);
		spLogging.setViewportView(taLogging);
		taLogging.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		taLogging.setBorder(new EmptyBorder(0, 0, 0, 0));
		taLogging.setAutoscrolls(true);
		
		btClear = new JButton("CLEAR");
		btClear.setFont(new Font("Arial", Font.PLAIN, 11));
		btClear.addActionListener(this);
		
		cbWrapLine = new JCheckBox("Line wrap");
		cbWrapLine.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cbWrapLine.setFont(new Font("Dialog", Font.PLAIN, 11));
		cbWrapLine.addChangeListener(this);
		cbWrapLine.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(10)
							.addComponent(spLogging, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(cbWrapLine)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btClear)))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(8)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
								.addComponent(btClear, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbWrapLine))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spLogging, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(28)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)))
					.addGap(14))
		);
		panel_3.setLayout(gl_panel_3);
		tabActivity.setLayout(gl_tabActivity);
		
		JTabbedPane tabDataConfig = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Data", null, tabDataConfig, null);
		
		JPanel panelResponse = new JPanel();
		tabDataConfig.addTab("Response", null, panelResponse, null);
		
		btSaveResponse = new JButton("Save");
		btSaveResponse.addActionListener(this);
		JScrollPane spDataResponse = new JScrollPane();
		GroupLayout gl_panelResponse = new GroupLayout(panelResponse);
		gl_panelResponse.setHorizontalGroup(
			gl_panelResponse.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelResponse.createSequentialGroup()
					.addContainerGap()
					.addComponent(btSaveResponse, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
				.addComponent(spDataResponse, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
		);
		gl_panelResponse.setVerticalGroup(
			gl_panelResponse.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelResponse.createSequentialGroup()
					.addComponent(spDataResponse, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btSaveResponse)
					.addGap(4))
		);
		
		txDataResponse = new JTextArea();
		spDataResponse.setViewportView(txDataResponse);
		panelResponse.setLayout(gl_panelResponse);
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Data", null, layeredPane, null);
		frmVsim.getContentPane().setLayout(groupLayout);
	}

	private void initializeObjectContent() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				initTabActivityContent();
				initTabDataContent();
			}
		}).start();
	}
	
	/***
	 * Init tab activity content
	 */
	private void initTabActivityContent() {
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
		for(HEADER_TYPE h : HEADER_TYPE.values()) {
			listbxHeaderType.addItem(h.name());
		}
		
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
	
	/***
	 * Init tab data content
	 */
	private void initTabDataContent() {
		txDataResponse.setText(IOFile.ReadFile(FILE_DATA_RESPONSE));
	}
	
	public Configuration getConfiguration() throws ISOException {
		Configuration config = new Configuration();
		HeaderConfig header = getHeaderConfit();
		
		if(cbMode.isSelected()) {
			config.setIp("");
		}else {
			config.setIp(txIP.getText());
		}
		
		config.setPort(Integer.parseInt(txPort.getText()));
		config.setCustomPackager(listbxPackager.getSelectedItem().toString().replace(TAG_DEFAULT, ""));
		config.setHeaderConfig(header);
		return config;
	}
	
	private HeaderConfig getHeaderConfit() {
		HeaderConfig header = new HeaderConfig();
		header.setHeaderType(HEADER_TYPE.valueOf(listbxHeaderType.getSelectedItem().toString()));
		if(cbCustomHeader.isSelected()) {
			header.setHeaderLength(Integer.parseInt(txHeaderLength.getText()));
			header.setStartValue(txHeaderStartValue.getText());
			header.setMiddleValue(txHeaderMidValue.getText());
			header.setEndValue(txHeaderEndValue.getText());
		}
		return header;
	}
	
	private void setEnableCustomHeader(boolean isSelected) {
		txHeaderStartValue.setEnabled(isSelected);
		txHeaderMidValue.setEnabled(isSelected);
		txHeaderEndValue.setEnabled(isSelected);		
	}
	
	private void saveDataResponse() {
		Object[] options = {"Ya","Tidak"};
		int n = JOptionPane.showOptionDialog(null,
		    "Apakah anda benar ingin simpan data response?",
		    "Save", JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);
		if(n == 0) {
			IOFile.WriteFile(FILE_DATA_RESPONSE, "", txDataResponse.getText(), "", false);
			initTabDataContent();
		}
	}
	
	/***
	 * Action Listener 
	 */
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
		}else if(obj instanceof JButton) {
			JButton button = (JButton) obj;
			if(button.equals(btClear)) {
				taLogging.setText("");
			}else if(button.equals(btSaveResponse)) {
				saveDataResponse();
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object obj = e.getSource();
		if(obj instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) obj;
			if(cb.equals(cbWrapLine)) {
				taLogging.setLineWrap(cb.isSelected());
			}else if(cb.equals(cbMode)){
				if(cbMode.isSelected()) {
					cbMode.setText("Server mode");
					txIP.setEnabled(false);
				}else {
					cbMode.setText("Client mode");
					txIP.setEnabled(true);
				}
			}else if(cb.equals(cbCustomHeader)) {
				setEnableCustomHeader(cb.isSelected());
			}
		}
	}
}
