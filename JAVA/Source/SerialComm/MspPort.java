import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import gnu.io.*;


public class MspPort implements SerialPortEventListener {

	private int baud_rate = 9600;
	//private int baud_rate = 115200;
	private int stop_bits = SerialPort.STOPBITS_1;
	private int flow_control = SerialPort.FLOWCONTROL_NONE;
	private int data_bits = SerialPort.DATABITS_8;
	private int parity_bits = SerialPort.PARITY_NONE;
	private SerialPort serial_port;
	private InputStream input;
	private OutputStream output;
	private String port_name;
	private boolean connected;
	private String buffer;
	
	
	//open and return an object of MspPort. This is a SerialPort object.
	public MspPort (CommPortIdentifier commPortId) {
		try {
			CommPort openPort = commPortId.open("MspPort", 5000);
			SerialPort serialPort = (SerialPort)openPort;
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();
			port_name = serialPort.getName();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			serial_port = serialPort;
			connected = true;
			buffer = new String("");
		}//end try 
		catch (PortInUseException | IOException | TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connected = false;
		}//end catch
	}//end constructor
	
	
	//method to catch and incoming data from the port
	@Override
	public void serialEvent(SerialPortEvent arg0) {
		InputStream eventData = this.get_input_stream();
		try {
			byte eventByte = (byte)eventData.read();
			byte[] eventArr = {eventByte};
			//did we get a text echo?
			if(buffer.contains("text")) 
			{
				//reset the buffer
				buffer = new String("");
				GUI.printInputData(eventByte);
			}
			//did we get an adc result?
			else if(buffer.contains("adc"))
			{
				Integer integer = (int)eventByte;
				//normalize the integer
				integer *= 8;
				//reset buffer
				buffer = new String("");
				//print the ADC
				GUI.printADC10Results(integer);
			}
			//set the byte
			buffer = new String(buffer.concat(new String(eventArr)));
		} 
		catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		}
	}
	
	//method to send data to serial port
	public boolean sendData(String outputString) {
		byte[] byteString = new byte[outputString.length()];
		for( int i=0; i < outputString.length(); i++) {
			byteString[i] = (byte) outputString.charAt(i);
		}
		try{
			serial_port.setSerialPortParams(baud_rate, data_bits, stop_bits, parity_bits);
			serial_port.setFlowControlMode(flow_control);
			output = serial_port.getOutputStream();
			output.write(byteString);
			return true;
		}
		catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			return false;
		}//end catch Unsupported 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendData(int outputInt) {
		try{
			serial_port.setSerialPortParams(baud_rate, data_bits, stop_bits, parity_bits);
			serial_port.setFlowControlMode(flow_control);
			output = serial_port.getOutputStream();
			output.write(outputInt);			
		}
		catch (UnsupportedCommOperationException e) {
			e.printStackTrace();
			return false;
		}//end catch Unsupported 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public SerialPort get_serial_port() {
		return serial_port;
	}
	public int get_baud_rate() {
		return baud_rate;
	}
	public int get_data_bits() {
		return data_bits;
	}
	public int get_stop_bits() {
		return stop_bits;
	}
	public int get_flow_control() {
		return flow_control;
	}
	public int get_parity_bits() {
		return parity_bits;
	}
	public String get_port_name() {
		return port_name;
	}
	public InputStream get_input_stream() {
		return input;
	}
	public OutputStream get_output_stream() {
		return output;
	}
	public boolean get_connected(){
		return connected;
	}
	public void set_baud_rate(int inBaudRate) {
		baud_rate = inBaudRate;
	}
	public void set_data_bits(int inDataBits) {
		data_bits = inDataBits;
	}
	public void set_parity_bits(int inParityBits) {
		parity_bits = inParityBits;
	}
	public void set_stop_bits(int inStopBits) {
		stop_bits = inStopBits;
	}
	public void set_flow_control(int inFlowControl) {
		flow_control = inFlowControl;
	}
}
