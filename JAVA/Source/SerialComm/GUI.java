import gnu.io.SerialPort;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

import java.awt.GridLayout;

import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.ComponentOrientation;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.JCheckBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GUI {

	private JFrame frmMspJavaSerial;
	private JLabel txtUartSettings;
	private JTextField outputDataField;
	private JTextPane uartResult;
	private static JTextArea ADC10Results;
	public static JTextArea displayInputField;
	private MspPort currentPort = JavaMsp.MSP_port_list.get(0);
	private Timer timer = new Timer();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmMspJavaSerial.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmMspJavaSerial = new JFrame();
		frmMspJavaSerial.setTitle("MSP430 Java Serial Communications");
		frmMspJavaSerial.setName("frame");
		frmMspJavaSerial.setBounds(100, 100, 946, 505);
		frmMspJavaSerial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMspJavaSerial.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		frmMspJavaSerial.getContentPane().add(panel);
		panel.setLayout(new GridLayout(7, 0, 0, 0));
		
		JPanel menuBar = new JPanel();
		menuBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		menuBar.setName("menuBar");
		panel.add(menuBar);
		menuBar.setLayout(null);
		
		
		
		final JComboBox baudRateButton = new JComboBox();
		baudRateButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(baudRateButton.getSelectedIndex() == 1) {
					currentPort.set_baud_rate(9600);
				}
			}
		});
		baudRateButton.setBounds(338, 11, 85, 20);
		baudRateButton.setModel(new DefaultComboBoxModel(new String[] {"Baud Rate", "9600"}));
		baudRateButton.setName("baudRateButton");
		menuBar.add(baudRateButton);
		
		
		final JComboBox dataBitsButton = new JComboBox();
		dataBitsButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(dataBitsButton.getSelectedIndex() == 1)
					currentPort.set_data_bits(SerialPort.DATABITS_5);
				else if(dataBitsButton.getSelectedIndex() == 2)
					currentPort.set_data_bits(SerialPort.DATABITS_6);
				else if(dataBitsButton.getSelectedIndex() == 3)
					currentPort.set_data_bits(SerialPort.DATABITS_7);
				else if(dataBitsButton.getSelectedIndex() == 4)
					currentPort.set_data_bits(SerialPort.DATABITS_8);
			}
		});
		dataBitsButton.setBounds(433, 11, 86, 20);
		dataBitsButton.setModel(new DefaultComboBoxModel(new String[] {"Data Bits", "5 Bits", "6 Bits", "7 Bits", "8 Bits"}));
		dataBitsButton.setName("dataBitsButton");
		menuBar.add(dataBitsButton);
		
		
		
		final JComboBox parityBitsButton = new JComboBox();
		parityBitsButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(parityBitsButton.getSelectedIndex() == 1)
					currentPort.set_parity_bits(SerialPort.PARITY_ODD);
				else if(parityBitsButton.getSelectedIndex() == 2)
					currentPort.set_parity_bits(SerialPort.PARITY_EVEN);
				else if(parityBitsButton.getSelectedIndex() == 3)
					currentPort.set_parity_bits(SerialPort.PARITY_MARK);
				else if(parityBitsButton.getSelectedIndex() == 4)
					currentPort.set_parity_bits(SerialPort.PARITY_SPACE);
				else if(parityBitsButton.getSelectedIndex() == 5)
					currentPort.set_parity_bits(SerialPort.PARITY_NONE);
			}
		});
		parityBitsButton.setBounds(529, 11, 85, 20);
		parityBitsButton.setModel(new DefaultComboBoxModel(new String[] {"Parity Bits", "Odd", "Even", "Mark", "Space", "None"}));
		parityBitsButton.setName("parityBitsButton");
		menuBar.add(parityBitsButton);
		
		
		
		final JComboBox stopBitsButton = new JComboBox();
		stopBitsButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(stopBitsButton.getSelectedIndex() == 1)
					currentPort.set_stop_bits(SerialPort.STOPBITS_1);
				else if(stopBitsButton.getSelectedIndex() == 2)
					currentPort.set_stop_bits(SerialPort.STOPBITS_1_5);
				else if(stopBitsButton.getSelectedIndex() == 3)
					currentPort.set_stop_bits(SerialPort.STOPBITS_2);
			}
		});
		stopBitsButton.setBounds(624, 11, 79, 20);
		stopBitsButton.setName("stopBitsButton");
		stopBitsButton.setModel(new DefaultComboBoxModel(new String[] {"Stop Bits", "1 Bits", "1.5 Bits", "2 Bits"}));
		menuBar.add(stopBitsButton);
		
		
		
		final JComboBox flowControlButton = new JComboBox();
		flowControlButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(flowControlButton.getSelectedIndex() == 1)
					currentPort.set_flow_control(SerialPort.FLOWCONTROL_NONE);
			}
		});
		flowControlButton.setBounds(713, 11, 85, 20);
		flowControlButton.setName("flowControlButton");
		flowControlButton.setModel(new DefaultComboBoxModel(new String[] {"Flow Control", "None"}));
		menuBar.add(flowControlButton);
		
		txtUartSettings = new JLabel();
		txtUartSettings.setText("UART Settings: ");
		txtUartSettings.setBounds(235, 11, 93, 20);
		menuBar.add(txtUartSettings);
		
		JLabel lblCommPort = new JLabel("Comm Port: ");
		lblCommPort.setBounds(10, 14, 79, 14);
		menuBar.add(lblCommPort);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				int selectedPort = comboBox.getSelectedIndex();
				currentPort = JavaMsp.MSP_port_list.get(selectedPort);
			}
		});
		String[] availablePorts = new String[JavaMsp.MSP_port_list.size() +1];
		availablePorts[0] = "None";
		for(int i=0; i < JavaMsp.MSP_port_list.size(); i++) {
			availablePorts[i] = JavaMsp.MSP_port_list.get(i).get_port_name();
		}
		comboBox.setModel(new DefaultComboBoxModel(availablePorts));
		comboBox.setBounds(99, 11, 126, 20);
		menuBar.add(comboBox);
		
		JPanel menuSettings = new JPanel();
		menuSettings.setName("menuSettings");
		panel.add(menuSettings);
		menuSettings.setLayout(null);
		
		final JLabel uartEchoLabel = new JLabel("UART Echo:");
		uartEchoLabel.setName("uartEchoLabel");
		uartEchoLabel.setBounds(10, 0, 78, 33);
		menuSettings.add(uartEchoLabel);
		uartResult = new JTextPane();
		uartResult.setBounds(343, 35, 86, 20);
		menuSettings.add(uartResult);
		outputDataField = new JTextField();
		outputDataField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//				currentPort.sendData(0x08);
				currentPort.sendData(30);
			}
		});
		outputDataField.setDragEnabled(true);
		outputDataField.setDoubleBuffered(true);
		outputDataField.setName("outputDataField");
		outputDataField.setBounds(98, 6, 104, 20);
		//setup the listener for input to send to UART port
		outputDataField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {}
			//grab the text from the text area.
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				int changeLength = arg0.getLength();
				int changePosition = arg0.getOffset();
				String string = null;
				//try getting just the last char entered
				try {
					string = outputDataField.getText(changePosition, changeLength);
				} 
				catch (BadLocationException e) {
					e.printStackTrace();
				}
				//send the data and update sent status
				if(!currentPort.sendData(string)) {
						uartResult.setText("Failed to Send");
						uartResult.setForeground(Color.RED);
				}
				else {
					uartResult.setText("Completed!");
					uartResult.setForeground(Color.GREEN);
				}					
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
			}			
		});
		menuSettings.add(outputDataField);
		outputDataField.setColumns(10);
		
		JLabel lblRecievedByte = new JLabel("Recieved Byte:");
		lblRecievedByte.setBounds(231, 0, 102, 33);
		menuSettings.add(lblRecievedByte);
		
		displayInputField = new JTextArea();
		displayInputField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.LIGHT_GRAY, Color.GRAY));
		displayInputField.setName("displayInputField");
		displayInputField.setEditable(false);
		displayInputField.setBounds(343, 6, 86, 20);
		menuSettings.add(displayInputField);
		
		JLabel commPortStatus = new JLabel("Port Status");
		if(currentPort.get_connected()){
			commPortStatus.setText("Connected!");
			commPortStatus.setForeground(Color.GREEN);
		}
		else {
			commPortStatus.setText("Not Connected!");
			commPortStatus.setForeground(Color.RED);
		}
		commPortStatus.setName("commPortStatus");
		commPortStatus.setBounds(538, 6, 104, 33);
		menuSettings.add(commPortStatus);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayInputField.setText("");
			}
		});
		btnClear.setBounds(439, 5, 89, 23);
		menuSettings.add(btnClear);
		
		
	}//end initialize()
	
	
	//method to write to display area for returning UART data
	public static void printInputData(byte inByte) {
		//displayInputField.setText("");
		//get a byte array for creating string
		byte[] byteArr = {inByte};
		String uartEcho = new String(byteArr);
		displayInputField.append(uartEcho);
	}//end printInputData
	
}
