import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * The Class SendMessage.
 */
public class SendMessage {

	 /** The port. */
 	private int port;
	 
 	/** The ip. */
 	private String ip;

	 /**
 	 * Instantiates a new send message.
 	 *
 	 * @param port the port
 	 * @param ip the ip
 	 */
 	public SendMessage(String port, String ip) {
		 this.port = Integer.parseInt(port);
		 this.ip = ip;
	 }

	/**
	 * Send.
	 *
	 * @param point the point
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void send(Point point) throws IOException {
		try {
			DatagramSocket socket = new DatagramSocket();
			InetAddress ipAdress = InetAddress.getByName(ip);  
			String data = Integer.toString(point.x) + " " + Integer.toString(point.y);
		        byte[] bytes = data.getBytes();
		        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, ipAdress, port);  
			socket.send(packet);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
