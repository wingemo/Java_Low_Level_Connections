/*
 * 
 */
import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

/**
 * The Class ReceivingMessage.
 */
public class ReceivingMessage implements Runnable {

	 /** The socket. */
 	private DatagramSocket socket;
	 
 	/** The paper. */
 	private Paper paper;
	 
 	/** The port. */
 	private String port;
	 
 	/** The t. */
 	private Thread t = new Thread(this);
	 
	  
	 /**
 	 * Instantiates a new receiving message.
 	 *
 	 * @param paper the paper
 	 * @param port the port
 	 */
 	public ReceivingMessage(Paper paper, String port) {
		 
		this.paper = paper;
		 
		 try {
			socket = new DatagramSocket(Integer.parseInt(port));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 t.start();
		 
	 }

	 
	/**
	 * Run.
	 */
	@Override
	public void run() {
		 try {
			 while(true) {
				 byte[] buf = new byte[1024];
				 DatagramPacket packet = new DatagramPacket(buf, buf.length);
				 socket.receive(packet);
				 String data = new String(packet.getData(),0, packet.getLength());
				 String[] xy = data.split(" ");
				 Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
				 this.paper.addPoint(p);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
