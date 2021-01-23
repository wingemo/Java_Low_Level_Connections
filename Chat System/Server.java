import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The Class Server.
 */
public class Server extends Application implements Runnable {

  /** The primary stage. */
  private Stage primaryStage;

  /** The root. */
  private BorderPane root;

  /** The scene. */
  private Scene scene;

  /** The display. */
  private TextArea display;

  /** The socket. */
  private ServerSocket socket;

  /** The hash set. */
  private HashSet < ChatClient > hashSet = new HashSet < ChatClient > ();

  /** The clients. */
  private int clients;

  /** The port. */
  private String port;

  /**
	 * The main method.
	 *
	 * @param args the arguments
	 */
  public static void main(String[] args) {
    launch(args);

  }

  /**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 */
  @Override
  public void start(Stage primaryStage) {
    Thread thread = new Thread(this);
    this.primaryStage = primaryStage;
    argsInput();
    stageConfig();
    addUiComponents();

    try {
      this.socket = new ServerSocket(Integer.parseInt(port));
      thread.start();
    } catch(IOException e) {
      e.printStackTrace();
    }

  }

  /**
	 * Args input.
	 */
  public void argsInput() {
    try {
      this.port = getParameters().getUnnamed().get(0);
    } catch(Exception e) {
      if (port == null) {
        port = "2000";
      }
    }
  }

  /**
	 * Stage config.
	 */
  private void stageConfig() {
    root = new BorderPane();
    scene = new Scene(root, 450, 300);
    primaryStage.setTitle(port + " " + clients);
    primaryStage.setScene(scene);
    primaryStage.show();
    primaryStage.setOnCloseRequest(event ->{
      System.exit(0);
    });

  }

  /**
	 * Gui config.
	 */
  private void addUiComponents() {
    display = new TextArea();
    display.setEditable(false);
    root.setCenter(display);
  }

  private void setTitle() {
    primaryStage.setTitle(port + " " + clients);
  }

  /**
	 * Client add.
	 *
	 * @param clientSocket the client socket
	 */
  public void clientAdd(Socket clientSocket) {
    ChatClient client = new ChatClient(clientSocket, this);
    hashSet.add(client);
    clients = clients + 1;
  }

  /**
	 * Broadcast.
	 *
	 * @param client the client
	 */
  public synchronized void delete(ChatClient client, String str) {
    hashSet.remove(client);
    client = null;
    clients = clients - 1;
    send(str);
    Platform.runLater(new Runnable() {@Override
      public void run() {
        setTitle();
      }
    });
  }

  /**
	 * Broadcast.
	 *
	 * @param str the str
	 */
  public synchronized void send(String str) {
    display.appendText("\n" + str);
    Iterator < ChatClient > iterator = hashSet.iterator();
    while (iterator.hasNext()) {
      ChatClient obj = iterator.next();
      obj.sendText(str);
    }
  }

  /**
	 * Run.
	 */
  @Override
  public void run() {
    while (true) {
      try {
        Socket clientSocket = socket.accept();
        clientAdd(clientSocket);
        Platform.runLater(new Runnable() {@Override
          public void run() {
            setTitle();
          }
        });
      } catch(IOException e) {
        e.printStackTrace();
      }
    }

  }

}
