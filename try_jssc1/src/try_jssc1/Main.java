package try_jssc1;

import java.util.concurrent.TimeUnit;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class Main {
	private static SerialPort port;

	public static void main(String[] args) {
		PrintComList();
		port = new SerialPort("COM4");

		try {
			port.openPort();
			port.setParams(	115200,
					8, 
					1, 
					SerialPort.PARITY_NONE);
			
			
			port.setEventsMask(SerialPort.MASK_RXCHAR);
			port.addEventListener(new EventListener());
			
			
			while (true) {
				try {
					//TimeUnit.SECONDS.sleep(1);
					TimeUnit.MILLISECONDS.sleep(20);
				} catch (InterruptedException e) {
					System.out.println(e);
				}
			byte BArr[] = {0x3A,
					0x30, 0x33,
					0x30, 0x33,
					0x30, 0x30,
					0x30, 0x31,
					0x30, 0x30,
					0x30, 0x31,
					0x46, 0x38,
					0x0D, 0x0A}; 
				//port.writeString(":030300010001F8");
				port.writeBytes(BArr);
			}
			
			//port.closePort();
			
		}catch (SerialPortException ex) {
			System.out.println(ex);
		}

	}
	
	static void PrintComList() {
		String[] portList = SerialPortList.getPortNames();
		
		for (int i = 0; i < portList.length; i++)
			System.out.println(portList[i]);
	}
	
	private static class EventListener implements SerialPortEventListener {
		public void serialEvent(SerialPortEvent event) {
			System.out.println("rx event");
			if (event.isRXCHAR())  {
				try {
					byte Buffer[] = port.readBytes();
					
					for (int i = 0; i < Buffer.length; i++)
						//System.out.println(Buffer[i]);
					System.out.printf("%x", Buffer[i]);
				} catch (SerialPortException ex) {
					System.out.println(ex);
				}
			}
			
		}	
	}

	
}

