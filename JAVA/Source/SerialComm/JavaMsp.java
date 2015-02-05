import java.util.ArrayList;
import java.util.Enumeration;
import gnu.io.*;


public class JavaMsp {

	public static ArrayList<MspPort> MSP_port_list = new ArrayList<MspPort>();
	
	public static void main(String[] args) {
		//open Enumeration for ports
		Enumeration<?> port_list = CommPortIdentifier.getPortIdentifiers();
		//while we have a list
		while (port_list.hasMoreElements()) {
			//get the element
			CommPortIdentifier port_id = (CommPortIdentifier)port_list.nextElement();
			//got a serial? add it to the list!
			if(port_id.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				MspPort found_port = new MspPort(port_id);
				MSP_port_list.add(found_port);
			}
		}//end while (port_list.hasmoreelemtns
		
		for(int i=0; i < MSP_port_list.size(); i++) {
			System.out.println(i + " : " +MSP_port_list.get(i).get_port_name());
		}
		//lauch the gui
		GUI.main(args);
	}//end main()

}//end JavaMsp


