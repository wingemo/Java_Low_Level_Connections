import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;

/**
 * The Class Draw.
 */
public class Draw extends JFrame {
  
  /** The p. */
  private static Paper p;

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    String clientPort = args[0];
    String ip = args[1];
    String serverPort = args[2];
    p = new Paper(clientPort, ip, serverPort);
    new Draw();
  }

  /**
   * Instantiates a new draw.
   */
  public Draw() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().add(p, BorderLayout.CENTER);
    setSize(640, 480);
    setVisible(true);

  }
}

class Paper extends JPanel {
  private HashSet hs = new HashSet();
  private SendMessage sendData;
  private ReceivingMessage receivie;
  
  public Paper(String clientPort, String ip, String serverPort) {
    setBackground(Color.black);
    addMouseListener(new L1());
    addMouseMotionListener(new L2()); 
    receivie = new ReceivingMessage(this, clientPort);
    sendData = new SendMessage(serverPort, ip);
  }

  public synchronized void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.white);
    Iterator i = hs.iterator();
    while(i.hasNext()) {
      Point p = (Point)i.next();
      g.fillOval(p.x, p.y, 2, 2);
    }
  }
  
  
  public synchronized void sendPoint(Point p) {
	try {
		sendData.send(p);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
  
  public synchronized void addPoint(Point p) {
    hs.add(p);
    repaint();
  }

  class L1 extends MouseAdapter {
    public void mousePressed(MouseEvent me) {
    sendPoint(me.getPoint());
    addPoint(me.getPoint());
    }
  }

  class L2 extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent me) {
    sendPoint(me.getPoint());
    addPoint(me.getPoint());
    }
  }
}
