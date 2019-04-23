package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    // windows.setScene(noQuizScene())
    takeQuiz.setPrefHeight(150);
    takeQuiz.setPrefWidth(250);
    takeQuiz.setFont(Font.font("Comic Sans MS", 25));
    if (Quiz.numberOfQuestion > 0) {
      takeQuiz.setOnAction(e -> windows.setScene(quizSelectionScene()));
    } else if (Quiz.numberOfQuestion == 0) {
      takeQuiz.setOnAction(e -> {
        Alert alert = new Alert(AlertType.INFORMATION, "No Quesiton in the database yet");
        alert.showAndWait().filter(response -> response == ButtonType.OK);
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
    GridPane root = new GridPane();
    root.setPadding(new Insets(15));
    root.setHgap(5);
    root.setVgap(5);
    // root.setAlignment(Pos.CENTER);
    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));


    Label pageLabel = new Label();
    pageLabel.setText("Quiz Construction");
    pageLabel.setFont(Font.font("Times New Roman", 40));
    pageLabel.setMaxHeight(100);
    root.add(pageLabel, 0, 0);

    TextField topicInput = new TextField("Enter a topic");
    topicInput.setPrefSize(200, 100);
    topicInput.setFont(Font.font("Times New Roman", 30));
    HBox topicBox = new HBox(topicInput);
    // String topic = topicInput.getText();
    // System.out.print( topic);
    root.add(topicBox, 0, 2);

    Label description = new Label();
    description.setText("Quiz Description");
    description.setFont(Font.font("Times New Roman", 40));
    description.setMaxHeight(100);
    root.add(description, 0, 3);

    TextArea desInput = new TextArea("Enter the descprition");
    desInput.setWrapText(true);
    desInput.setPrefSize(600, 200);
    desInput.setFont(Font.font("Times New Roman", 20));
    // String topic = topicInput.getText();
    // System.out.print( topic);
    HBox desBox = new HBox(desInput);
    // String topic = topicInput.getText();
    // System.out.print( topic);
    root.add(desBox, 0, 4);


    ToggleGroup tg = new ToggleGroup();
    VBox vbox = new VBox();
    vbox.setSpacing(5);

    RadioButton rb1 = new RadioButton();
    rb1.setToggleGroup(tg);
    TextField choiceInput1 = new TextField("Choice1 Input");
    choiceInput1.setPrefSize(400, 50);
    choiceInput1.setFont(Font.font("Times New Roman", 20));
    rb1.setSelected(true);

    HBox hbox1 = new HBox(rb1, choiceInput1);

    RadioButton rb2 = new RadioButton();
    rb2.setToggleGroup(tg);
    rb2.setSelected(false);
    TextField choiceInput2 = new TextField("Choice2 Input");
    choiceInput2.setPrefSize(400, 50);
    choiceInput2.setFont(Font.font("Times New Roman", 20));


    HBox hbox2 = new HBox(rb2, choiceInput2);

    RadioButton rb3 = new RadioButton();
    rb3.setToggleGroup(tg);
    rb3.setSelected(false);
    TextField choiceInput3 = new TextField("Choice3 Input");
    choiceInput3.setPrefSize(400, 50);
    choiceInput3.setFont(Font.font("Times New Roman", 20));
    HBox hbox3 = new HBox(rb3, choiceInput3);

    RadioButton rb4 = new RadioButton();
    rb4.setToggleGroup(tg);
    rb4.setSelected(false);
    TextField choiceInput4 = new TextField("Choice4 Input");
    choiceInput4.setPrefSize(400, 50);
    choiceInput4.setFont(Font.font("Times New Roman", 20));
    HBox hbox4 = new HBox(rb4, choiceInput4);

    RadioButton rb5 = new RadioButton();
    rb5.setToggleGroup(tg);
    rb5.setSelected(false);
    TextField choiceInput5 = new TextField("Choice5 Input");
    choiceInput5.setPrefSize(400, 50);
    choiceInput5.setFont(Font.font("Times New Roman", 20));
    HBox hbox5 = new HBox(rb5, choiceInput5);



    vbox.getChildren().add(hbox1);
    vbox.getChildren().add(hbox2);
    vbox.getChildren().add(hbox3);
    vbox.getChildren().add(hbox4);
    vbox.getChildren().add(hbox5);
    root.add(vbox, 0, 6);

    Label imageImport = new Label();
    imageImport.setText("Image Import");
    imageImport.setFont(Font.font("Times New Roman", 20));
    imageImport.setMaxHeight(100);


    TextField imageInput = new TextField("(Enter file path)");
    imageInput.setPrefSize(200, 100);
    imageInput.setFont(Font.font("Times New Roman", 20));

    VBox imagePath = new VBox(imageImport, imageInput);
    imagePath.setAlignment(Pos.CENTER_LEFT);
    root.add(imagePath, 1, 6);


    Button add = new Button("Submit");
    add.setPrefSize(200, 100);
    add.setFont(Font.font("Times New Roman", 20));
    root.add(add, 1, 7);
    EventHandler<ActionEvent> addHandler = new ManualAddQuestion(add);
    // everytime an action happens
    add.setOnAction(addHandler);

    Button back = new Button("Back");
    back.setPrefSize(200, 100);
    back.setFont(Font.font("Times New Roman", 20));
    root.add(back, 0, 7);
    back.setOnAction(e -> windows.setScene(startScene()));


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

  // private Scene noQuizScene() {
  // BorderPane root = new BorderPane();
  // Label errorMessage = new Label();
  // errorMessage.setText("No quiz in current data base,\n please import or create
  // quiz first");
  // errorMessage.setFont(Font.font("Times New Roman", 40));
  // // homePage.setMaxHeight(100);
  //
  //
  // Button back = new Button("Back");
  // back.setOnAction(e -> windows.setScene(startScene()));
  // VBox vbox = new VBox(errorMessage, back);
  // vbox.setAlignment(Pos.CENTER);
  // root.setCenter(vbox);
  // BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800,
  // 800, false, true),
  // BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null,
  // BackgroundSize.DEFAULT);
  // root.setBackground(new Background(myBI));
  //
  //
  //
  // Scene scene = new Scene(root, 800, 800);
  // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  // return scene;
  // }

  private Scene quizAnsweringScene() {

    BorderPane root = new BorderPane();
    Label pageLabel = new Label();
    pageLabel.setText("Quiz In Progress");
    pageLabel.setFont(Font.font("Times New Roman", 40));
    pageLabel.setMaxHeight(80);
    root.setTop(pageLabel);

    Button addMoreQuestion = new Button("Add more questions");
    addMoreQuestion.setPrefSize(200, 80);
    addMoreQuestion.setFont(Font.font("Times New Roman", 20));
    addMoreQuestion.setOnAction(e -> windows.setScene(quizImportScene()));

    Button check = new Button("Check");
    check.setPrefSize(200, 80);
    check.setFont(Font.font("Times New Roman", 45));
    check.setOnAction(e -> {
      Quiz.numberOfQuestionAnswered++;
      windows.setScene(answerDisplayScene());
    });

    VBox nextChoice = new VBox(addMoreQuestion, check);
    nextChoice.setSpacing(250);
    nextChoice.setAlignment(Pos.CENTER);
    root.setRight(nextChoice);

    ImageView questionPic = new ImageView(new Image("defaultQuestionBackground.jpg"));
    questionPic.setFitHeight(350);
    questionPic.setPreserveRatio(true);
    root.setCenter(questionPic);

    Label topicLabel = new Label("Current Topic");
    topicLabel.setPrefSize(140, 90);
    topicLabel.setFont(Font.font("Times New Roman", 20));
    Label quesAns = new Label("Answered: " + Quiz.numberOfQuestionAnswered);
    quesAns.setPrefSize(140, 90);
    quesAns.setFont(Font.font("Times New Roman", 20));
    Label quesAll = new Label("Total Question: " + Quiz.numberOfCurrentQuestion);
    quesAll.setPrefSize(140, 90);
    quesAll.setFont(Font.font("Times New Roman", 20));
    VBox condition = new VBox(topicLabel, quesAns, quesAll);
    condition.setSpacing(40);
    condition.setAlignment(Pos.CENTER);
    root.setLeft(condition);

    Label answerList = new Label("Answer: ");
    answerList.setFont(Font.font("Times New Roman", 30));
    answerList.setPrefSize(150, 100);
    answerList.setAlignment(Pos.CENTER);
    root.setBottom(answerList);

    // button2.setOnAction(e -> windows.setScene(buildScene0()));
    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 1000, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    Scene scene = new Scene(root, 1000, 800);
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
    message.setText("Final Result : ");

    message.setFont(Font.font("Times New Roman", 40));
    message.setMaxHeight(100);

    Label numCorrect = new Label("Number of correct questions: " + Quiz.numberOfCorrect);
    Label numAnswerd = new Label("Number of questions answers: " + Quiz.numberOfQuestionAnswered);
    Label percentScore = new Label(
        "Percent correct: " + Quiz.numberOfCorrect / Quiz.numberOfQuestionAnswered * 100 + "%");
    numCorrect.setPrefSize(600, 100);
    numAnswerd.setPrefSize(600, 100);
    percentScore.setPrefSize(600, 100);
    numCorrect.setFont(Font.font("Times New Roman", 40));
    numAnswerd.setFont(Font.font("Times New Roman", 40));
    percentScore.setFont(Font.font("Times New Roman", 40));
    VBox result = new VBox(numCorrect, numAnswerd, percentScore);
    result.setSpacing(50);
    result.setAlignment(Pos.CENTER);
    result.setMaxHeight(500);

    Button moreQuestion = new Button("Add Additional Question");
    moreQuestion.setPrefSize(200, 150);
    moreQuestion.setOnAction(e -> windows.setScene(quizImportScene()));
    Button exit = new Button("EXIT");
    exit.setPrefSize(200, 150);
    exit.setOnAction(e -> windows.setScene(saveQuizResult()));
    Button backHome = new Button("Back to HomePage");
    backHome.setPrefSize(200, 150);
    backHome.setOnAction(e -> windows.setScene(startScene()));

    HBox choice = new HBox(backHome, moreQuestion, exit);
    choice.setSpacing(50);
    choice.setAlignment(Pos.CENTER);

    root.setTop(message);
    root.setCenter(result);
    root.setBottom(choice);

    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    Scene scene = new Scene(root, 800, 800);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private Scene saveQuizResult() {
    BorderPane root = new BorderPane();

    Label prompt = new Label();
    prompt.setText("Do you wish to save all the questions to a json file?");
    prompt.setFont(Font.font("Times New Roman", 25));
    prompt.setAlignment(Pos.CENTER);

    Button save = new Button("Save");
    save.setPrefSize(250, 150);
    Button exit = new Button("Exit without Save");
    exit.setPrefSize(250, 150);
    exit.setOnAction(e -> {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setHeight(300);
      alert.setWidth(500);
      alert.setTitle("Warning!");
      alert.setContentText("Are you sure you want to exit without save now?");
      alert.getButtonTypes().clear();
      ButtonType confirmExit = new ButtonType("I confirm to exit", ButtonBar.ButtonData.RIGHT);
      alert.getButtonTypes().add(confirmExit);
      ButtonType cancel = new ButtonType("NO", ButtonBar.ButtonData.RIGHT);
      alert.getButtonTypes().add(cancel);
      ButtonBar.setButtonUniformSize(alert.getDialogPane().lookupButton(confirmExit), false);
      ButtonBar.setButtonUniformSize(alert.getDialogPane().lookupButton(cancel), false);
      alert.getDialogPane().lookupButton(confirmExit).addEventFilter(ActionEvent.ACTION,
          event -> windows.setScene(goodByePage()));
      alert.getDialogPane().lookupButton(cancel).addEventFilter(ActionEvent.ACTION,
          event -> windows.setScene(saveQuizResult()));
      alert.showAndWait();
    });
    HBox choice = new HBox(100);
    choice.getChildren().add(save);
    choice.getChildren().add(exit);
    choice.setAlignment(Pos.CENTER);

    root.setCenter(prompt);
    root.setBottom(choice);

    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 600, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  private Scene goodByePage() {
    BorderPane root = new BorderPane();
    Label message = new Label("Thank you for using Quiz Generator.\n See you next time.");
    message.setAlignment(Pos.CENTER);
    message.setFont(Font.font("Times New Roman", 45));
    message.setLineSpacing(20);
    root.setCenter(message);

    Button close = new Button("close the window now");
    close.setPrefSize(220, 80);
    close.setFont(Font.font("Times New Roman", 20));
    root.setBottom(close);

    // this button closes the entire program
    close.setOnAction(e -> Platform.exit());

    BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 600, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
    root.setBackground(new Background(myBI));

    Scene scene = new Scene(root, 800, 600);
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return scene;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
