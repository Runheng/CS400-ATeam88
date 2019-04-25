package application;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

   public PrimaryGUI primaryGUI;
  // Scene scene1, scenc2;
  @Override
  public void start(Stage primaryStage) {
    try {
      primaryGUI = new PrimaryGUI( primaryStage);
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }



  public static void main(String[] args) {
    launch(args);
  }
}
