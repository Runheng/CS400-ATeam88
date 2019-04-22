package application;

import application.Main.ManualAddQuestion;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
  Stage windows;

  // Scene scene1, scenc2;
  @Override
  public void start(Stage primaryStage) {
    try {
      windows = primaryStage;


      windows.setScene(startScene());
      windows.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Scene startScene() {
    BorderPane root = new BorderPane();

    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    // where implementation starts
    Label homePage = new Label();
    homePage.setText("Home Page" + "\nNumber of question in database: "
        + Integer.toString(Quiz.numberOfQuestion));

    // clickCountLabel.setText(Integer.toString(clickCount));
    homePage.setFont(Font.font("Times New Roman", 40));
    homePage.setMaxHeight(100);
    root.setTop(homePage);

    Label quizGen = new Label();
    quizGen.setText("Quiz Generator");
    quizGen.setFont(Font.font("Comic Sans MS", 90));
    quizGen.setPrefHeight(300);
    quizGen.setPrefWidth(800);
    quizGen.setAlignment(Pos.CENTER);
    root.setCenter(quizGen);

    // add button
    Button takeQuiz = new Button("Take a new quiz");
    Button importQuiz = new Button("Import quiz");
    //windows.setScene(noQuizScene())
    takeQuiz.setPrefHeight(150);
    takeQuiz.setPrefWidth(250);
    takeQuiz.setFont(Font.font("Comic Sans MS", 25));
    if (Quiz.numberOfQuestion > 0) {
      takeQuiz.setOnAction(e -> windows.setScene(quizSelectionScene()));
    } else if (Quiz.numberOfQuestion == 0) {
      takeQuiz.setOnAction(e -> {
        Alert alert = new Alert(AlertType.INFORMATION, "No Quesiton in the database yet");
        alert.showAndWait().filter(
            response-> response==ButtonType.OK);
      });
    }

    importQuiz.setPrefHeight(150);
    importQuiz.setPrefWidth(250);
    importQuiz.setFont(Font.font("Comic Sans MS", 25));
    importQuiz.setOnAction(e -> windows.setScene(quizImportScene()));
    HBox selection = new HBox(150);
    selection.getChildren().add(takeQuiz);
    selection.getChildren().add(importQuiz);
    selection.setAlignment(Pos.CENTER);
    root.setBottom(selection);


    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;


  }

  private Scene quizImportScene() {
    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);

    BorderPane root = new BorderPane();
    root.setBackground(new Background(myBI));
    HBox newQuestion = newQuestion();
    HBox importData = importData();
    VBox col1 = new VBox(newQuestion, importData);
    // set space
    col1.setSpacing(100);
    col1.setAlignment(Pos.CENTER);
    root.setCenter(col1);


    Label quizImport = new Label("Quiz Import");
    quizImport.setFont(Font.font("Times New Roman", 40));
    root.setTop(quizImport);


    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private HBox newQuestion() {
    Button createQuestion = new Button("Create a New Question");
    createQuestion.setFont(Font.font("Arial", 20));
    createQuestion.setPrefSize(400, 100);
    createQuestion.setOnAction(e -> windows.setScene(quizConstructScene()));
    HBox result = new HBox(createQuestion);



    return result;
  }

  private HBox importData() {
    Button importData = new Button("Import DataBase");
    importData.setFont(Font.font("Arial", 20));
    importData.setPrefSize(600, 100);
    TextField filePath = new TextField("(Enter file path)");
    filePath.setFont(Font.font("Arial", 20));
    filePath.setPrefSize(600, 100);
    // teamTextField.setMaxWidth(200);
    // teamTextField.setMaxHeight(100);
    HBox resultBox = new HBox(importData, filePath);
    return resultBox;
  }


  private Scene quizConstructScene() {
    BorderPane root = new BorderPane();
    Label homePage = new Label();
    homePage.setText("Quiz Construction");
    homePage.setFont(Font.font("Times New Roman", 40));
    homePage.setMaxHeight(100);
    root.setTop(homePage);

    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    Button add = new Button("Submit");
    EventHandler<ActionEvent> addHandler = new ManualAddQuestion(add);
    // everytime an action happens
    add.setOnAction(addHandler);

    Button back = new Button("Back");

    back.setOnAction(e -> windows.setScene(startScene()));

    VBox col1 = new VBox(add, back);
    // set space
    col1.setSpacing(100);
    col1.setAlignment(Pos.CENTER);
    root.setCenter(col1);



    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  class ManualAddQuestion implements EventHandler<ActionEvent> {
    Button button;

    ManualAddQuestion(Button b) {
      this.button = b;
    }

    @Override
    public void handle(ActionEvent e) {
      Quiz.numberOfQuestion++;
      System.out.println(Quiz.numberOfQuestion);

      // clickCountLabel.setText(Integer.toString(clickCount));

    }


  }

  private Scene quizSelectionScene() {

    BorderPane root = new BorderPane();
    Label pageLabel = new Label();
    pageLabel.setText("Quiz Selection");
    pageLabel.setFont(Font.font("Times New Roman", 40));
    pageLabel.setMaxHeight(100);
    root.setTop(pageLabel);

    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    ObservableList<String> topicOptions =
        FXCollections.observableArrayList("topic1", "topic2", "topic3", "topic4");
    final ComboBox<String> comboBox = new ComboBox<String>(topicOptions);


    HBox num = numOfQuestion();

    Button back = new Button("Back");
    back.setOnAction(e -> windows.setScene(startScene()));
    // button2.setOnAction(button1Handler);

    VBox col1 = new VBox(comboBox, num, back);
    // set space
    col1.setSpacing(100);
    col1.setAlignment(Pos.CENTER);
    root.setCenter(col1);


    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private HBox numOfQuestion() {
    Label importData = new Label("Scope Size");
    importData.setFont(Font.font("Arial", 20));
    importData.setPrefSize(200, 100);
    TextField filePath = new TextField("(Enter number of question you want to anser)");
    filePath.setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ENTER) {
        try {
          int num = Integer.parseInt(filePath.getText());
          Quiz.numberOfCurrentQuestion = num;
          filePath.setText("" + Quiz.numberOfCurrentQuestion);
          System.out.print(Quiz.numberOfCurrentQuestion);
          filePath.setEditable(false);
          windows.setScene(quizAnsweringScene());
          // is an integer!
        } catch (NumberFormatException exception) {
          filePath.setText("please enter an integer");
          // filePath.setEditable(false);
          // not an integer!
        }
      } ;
    });
    filePath.setFont(Font.font("Arial", 20));
    filePath.setPrefSize(400, 100);

    Button submit = new Button("Submit");
    submit.setOnAction(e -> {



    });
    submit.setFont(Font.font("Arial", 20));
    submit.setPrefSize(200, 100);
    // teamTextField.setMaxWidth(200);
    // teamTextField.setMaxHeight(100);
    HBox resultBox = new HBox(importData, filePath);
    return resultBox;
  }

  private Scene noQuizScene() {
    BorderPane root = new BorderPane();
    Label errorMessage = new Label();
    errorMessage.setText("No quiz in current data base,\n please import or create quiz first");
    errorMessage.setFont(Font.font("Times New Roman", 40));
    // homePage.setMaxHeight(100);


    Button back = new Button("Back");
    back.setOnAction(e -> windows.setScene(startScene()));
    VBox vbox = new VBox(errorMessage, back);
    vbox.setAlignment(Pos.CENTER);
    root.setCenter(vbox);
    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));



    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private Scene quizAnsweringScene() {

    BorderPane root = new BorderPane();
    Label pageLabel = new Label();
    pageLabel.setText("Quiz In Progress");
    pageLabel.setFont(Font.font("Times New Roman", 40));
    pageLabel.setMaxHeight(100);
    root.setTop(pageLabel);

    Button button2 = new Button("Submit");
    button2.setOnAction(e -> {
      Quiz.numberOfQuestionAnswered++;

      windows.setScene(answerDisplayScene());

    });

    root.setCenter(button2);
    // button2.setOnAction(e -> windows.setScene(buildScene0()));
    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));



    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private Scene answerDisplayScene() {
    BorderPane root = new BorderPane();
    Label message = new Label();
    message.setText("Your Answer is: ");
    message.setFont(Font.font("Times New Roman", 40));
    message.setMaxHeight(100);

    TextField filePath = new TextField();
    filePath.setFont(Font.font("Arial", 20));
    filePath.setPrefSize(200, 100);
    filePath.setText("" + Quiz.correct);
    // button2.setOnAction(e -> windows.setScene(buildScene0()));

    Button next = new Button("Next");
    next.setOnAction(e -> {
      if (Quiz.numberOfQuestionAnswered < Quiz.numberOfCurrentQuestion) {
        windows.setScene(quizAnsweringScene());
      } else if (Quiz.numberOfQuestionAnswered == Quiz.numberOfCurrentQuestion) {
        windows.setScene(scoreDisplayScene());
      }
    });
    HBox hbox = new HBox(message, filePath, next);
    hbox.setSpacing(10);
    root.setCenter(hbox);


    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));



    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private Scene scoreDisplayScene() {
    BorderPane root = new BorderPane();
    Label message = new Label();
    message.setText("Quiz Result : 100 ");
    message.setFont(Font.font("Times New Roman", 40));
    message.setMaxHeight(100);


    Button back = new Button("Back");
    back.setOnAction(e -> windows.setScene(startScene()));

    HBox hbox = new HBox(message, back);
    hbox.setSpacing(100);
    root.setCenter(hbox);


    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));



    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
