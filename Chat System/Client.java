import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The Class ChatClient.
 */
public class ChatClient extends Application implements Runnable {

  /** The primary stage. */
  private Stage primaryStage;

  /** The root. */
  private BorderPane root;

  /** The scene. */
  private Scene scene;

  /** The text field. */
  private TextField textField;

  /** The display. */
  private TextArea display;

  /** The hbox. */
  private HBox hbox;

  /** The button. */
  private Button button;

  /** The socket. */
  private Socket socket;

  /** The in. */
  private BufferedReader in ;

  /** The host. */
  private String host;

  /** The port. */
  private String port;

  /** The out. */
  private PrintWriter out;

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
    this.primaryStage = primaryStage;
    primaryStage.setOnCloseRequest(event ->{
      try {
        socket.close();
      } catch(IOException e) {}
    });

    argsInput();
    stageConfig();
    addGuiComponents();
    getInputData();
  }

  /**
   * Args input.
   */
  public void argsInput() {
    try {
      this.host = getParameters().getUnnamed().get(0);
      this.port = getParameters().getUnnamed().get(1);
    } catch(Exception e) {
      if (host == null && port == null) {
        host = "127.0.0.1";
        port = "2000";
        System.out.print(port);
      } else if (host == null) {
        host = "127.0.0.1";
      } else if (port == null) {
        port = "2000";
      }
    }
  }

  /**
   * Stage config.
   */
  private void stageConfig() {
    root = new BorderPane();
    scene = new Scene(root, 300, 300);
    primaryStage.setTitle("IP: " + this.host + " - PORT: " + this.port);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Adds the gui components.
   */
  private void addGuiComponents() {
    button = new Button("SEND");
    display = new TextArea();
    display.setEditable(false);
    textField = new TextField();
    textField.setPrefWidth(255);
    textField.setOnAction(new textFieldHandler());
    button.setOnAction(new textFieldHandler());
    hbox = new HBox(textField, button);
    root.setCenter(display);
    root.setBottom(hbox);
    textField.requestFocus();
  }

  /**
   * Gets the input data.
   *
   * @return the input data
   */
  private void getInputData() {
    try {
      int port = Integer.parseInt(this.port);
      socket = new Socket(host, port);
      out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1"), true); in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
      Thread thread = new Thread(this);
      thread.start();
    } catch(UnknownHostException e) {
      e.printStackTrace();
    } catch(IOException e) {
      Alert alert = new Alert(AlertType.ERROR, "The client failed to connect to the server", ButtonType.CLOSE);
      alert.showAndWait();
      System.exit(0);
    }
  }


  /**
   * The Class textFieldHandler.
   */
  class textFieldHandler implements EventHandler < ActionEvent > {

    /**
     * Handle.
     *
     * @param event the event
     */
    @Override
    public void handle(ActionEvent event) {
      String text = textField.getText();
      out.println(text);
      textField.setText("");
    }

  }

  /**
   * Run.
   */
  @Override
  public void run() {
    try {
      while (true) {
        String line = in.readLine();
        display.appendText(line + "\n");
      }
    } catch(IOException e) {
      e.printStackTrace();
    }

  }

}
