package application;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import org.json.simple.parser.ParseException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.stage.Stage;

public class PrimaryGUI {
	Stage windows;
	QuestionBank questionBank = new QuestionBank();
	CurrentQuiz currentQuiz = null;
	List<String> currentQuizTopic = new ArrayList<String>();
	List<Question> totalQuestionForTopic = new ArrayList<Question>();
	boolean currentCorrect = false;

	PrimaryGUI(Stage stage) {

		windows = stage;

		windows.setScene(startScene());
	}

	private Scene startScene() {
		BorderPane root = new BorderPane();

		BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
		root.setBackground(new Background(myBI));

		// where implementation starts
		Label homePage = new Label();
		homePage.setText(
				"Home Page" + "\nNumber of question in database: " + Integer.toString(questionBank.getNumQuestion()));

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
		if (questionBank.getNumQuestion() > 0) {
			takeQuiz.setOnAction(e -> windows.setScene(quizSelectionScene()));
		} else if (questionBank.getNumQuestion() == 0) {
			takeQuiz.setOnAction(e -> {
				Alert alert = new Alert(AlertType.WARNING, "No Quesiton in the database yet");
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

		Button back = new Button("Back");
		back.setPrefSize(200, 100);
		back.setFont(Font.font("Times New Roman", 30));

		back.setOnAction(e -> windows.setScene(startScene()));

		VBox col1 = new VBox(newQuestion, importData, back);
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
		TextField filePath = new TextField();
		filePath.setFont(Font.font("Arial", 20));
		filePath.setPrefSize(600, 100);
		// add warning and stuff

		importData.setOnAction(e -> {
			try {
				if (filePath.getText() == null || filePath.getText().trim().equals("")) {
					throw new InputMismatchException();
				} else {

					questionBank.addQuestionFile(filePath.getText());
					Alert CorrectinputAlert = new Alert(AlertType.INFORMATION);
					CorrectinputAlert.setTitle("Success!");
					CorrectinputAlert.setContentText("Questions has been added to the Question Database");
					CorrectinputAlert.setResizable(true);
					CorrectinputAlert.showAndWait();

				}
			} catch (FileNotFoundException e1) {

				Alert badInputAlert = new Alert(AlertType.WARNING);
				badInputAlert.setTitle("Invalid input!");
				badInputAlert.setContentText("Can not find this file!");
				badInputAlert.setResizable(true);
				badInputAlert.showAndWait();
				filePath.setStyle("-fx-text-box-border: red;");

			} catch (InputMismatchException e1) {

				Alert badInputAlert = new Alert(AlertType.WARNING);
				badInputAlert.setTitle("Invalid input!");
				badInputAlert.setContentText("Invalid input on 'file path' field! Must be a non-empty String!'");
				badInputAlert.setResizable(true);
				badInputAlert.showAndWait();
				filePath.setStyle("-fx-text-box-border: red;");

			} catch (IOException e1) {
				Alert badInputAlert = new Alert(AlertType.WARNING);
				badInputAlert.setTitle("Invalid input!");
				badInputAlert.setContentText("Can not open this file!");
				badInputAlert.setResizable(true);
				badInputAlert.showAndWait();
				filePath.setStyle("-fx-text-box-border: red;");
			} catch (ParseException e1) {
				Alert badInputAlert = new Alert(AlertType.WARNING);
				badInputAlert.setTitle("Invalid input!");
				badInputAlert.setContentText("file can not be parsed correctly!");
				badInputAlert.setResizable(true);
				badInputAlert.showAndWait();
				filePath.setStyle("-fx-text-box-border: red;");
			}

		});
		HBox resultBox = new HBox(importData, filePath);
		return resultBox;
	}

	private Scene quizConstructScene() {
		AddQuestion addQuestion = new AddQuestion();
		GridPane root = addQuestion.AddQuestionPage(questionBank);

		Button moreQuiz = new Button("Add One More Quiz");
		moreQuiz.setPrefSize(200, 100);
		moreQuiz.setFont(Font.font("Times New Roman", 20));
		moreQuiz.setOnAction(e -> windows.setScene(quizConstructScene()));

		Button back = new Button("Back");
		back.setPrefSize(200, 100);
		back.setFont(Font.font("Times New Roman", 20));

		back.setOnAction(e -> windows.setScene(startScene()));
		HBox hbox = new HBox(back, moreQuiz);
		root.add(hbox, 0, 7);

		Scene scene = new Scene(root, 800, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	private Scene quizSelectionScene() {

		BorderPane root = new BorderPane();
		Label pageLabel = new Label();
		pageLabel.setText("Choose topic and number of total questions");
		pageLabel.setFont(Font.font("Times New Roman", 30));
		pageLabel.setMaxHeight(100);
		root.setTop(pageLabel);

		BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 800, 800, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
		root.setBackground(new Background(myBI));

		ListView<String> listview = new ListView<>();
		for (String n : questionBank.getTopicList()) {
			listview.getItems().addAll(n);
			System.out.print(n);
		}

		ObservableList<String> topicOptions = listview.getItems();

		HBox hbox = new HBox();

		for (int i = 0; i < topicOptions.size(); i++) {

			ComboBox<String> topic = new ComboBox<String>(topicOptions);

			hbox.getChildren().add(topic);

		}
		hbox.setAlignment(Pos.CENTER);

		HBox num = numOfQuestion(hbox);

		Button back = new Button("Back");
		back.setPrefSize(100, 80);
		back.setFont(Font.font("Times New Roman", 25));
		back.setOnAction(e -> windows.setScene(startScene()));
		// button2.setOnAction(button1Handler);

		VBox col1 = new VBox(hbox, num, back);
		// set space
		col1.setSpacing(100);
		col1.setAlignment(Pos.CENTER);
		root.setCenter(col1);

		Scene scene = new Scene(root, 800, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	private HBox numOfQuestion(HBox hbox) {
		Label importData = new Label("Scope Size");
		importData.setFont(Font.font("Arial", 20));
		importData.setPrefSize(200, 100);

		TextField filePath = new TextField("Enter number of question you want to answer");

		filePath.setFont(Font.font("Arial", 20));
		filePath.setPrefSize(400, 100);

		currentQuizTopic = new ArrayList<String>();
		totalQuestionForTopic = new ArrayList<Question>();

		Button submit = new Button("Start quiz now!");

		submit.setOnAction(e -> {
			try {
				int num = Integer.parseInt(filePath.getText());

				if (num <= 0) {
					Alert badInputAlert = new Alert(AlertType.WARNING);
					badInputAlert.setTitle("Invalid input!");
					badInputAlert.setContentText("Invalid input on 'size' field! please put a non-negative integer'");
					badInputAlert.setResizable(true);
					badInputAlert.showAndWait();
					windows.setScene(quizSelectionScene());
					return;
				}

				boolean noSelect = true;
				// adding up the current quiz topic list
				for (int i = 0; i < hbox.getChildren().size(); i++) {
					ComboBox<String> current = (ComboBox<String>) hbox.getChildren().get(i);

					if (current.getValue() != null) {
						noSelect = false;
						currentQuizTopic.add(current.getValue());
					}
				}

				if (noSelect) {
					Alert badInputAlert = new Alert(AlertType.WARNING);
					badInputAlert.setTitle("Invalid input!");
					badInputAlert.setContentText("Invalid input on 'topic' field! please select a topic'");
					badInputAlert.setResizable(true);
					badInputAlert.showAndWait();
					windows.setScene(quizSelectionScene());
					return;
				}

				for (int i = 0; i < currentQuizTopic.size(); i++) {

					List<Question> questionInOneTopic = questionBank.getQuestionInOneTopic(currentQuizTopic.get(i));

					totalQuestionForTopic.addAll(questionInOneTopic);

				}
				currentQuiz = new CurrentQuiz(num, totalQuestionForTopic);

				windows.setScene(quizAnsweringScene());
				// is an integer!
			} catch (NumberFormatException exception) {
				Alert badInputAlert = new Alert(AlertType.WARNING);
				badInputAlert.setTitle("Invalid input!");
				badInputAlert.setContentText("Invalid input on 'size' field! please a non-negative integer'");
				badInputAlert.setResizable(true);
				badInputAlert.showAndWait();
				windows.setScene(quizSelectionScene());
				return;
			}
		});

		submit.setFont(Font.font("Arial", 20));
		submit.setPrefSize(200, 100);
		// teamTextField.setMaxWidth(200);
		// teamTextField.setMaxHeight(100);
		HBox resultBox = new HBox(importData, filePath, submit);
		return resultBox;
	}

	private Scene quizAnsweringScene() {

		QuizAnswering quizDisplay = new QuizAnswering();
		BorderPane root = quizDisplay.quizAnswering(currentQuiz);

		ToggleGroup choices = quizDisplay.getToggleGroup();

		Button addMoreQuestion = new Button("Add more questions");
		addMoreQuestion.setPrefSize(200, 80);
		addMoreQuestion.setFont(Font.font("Times New Roman", 20));
		addMoreQuestion.setOnAction(e -> windows.setScene(quizImportScene()));

		Button check = new Button("Check");
		check.setPrefSize(200, 80);
		check.setFont(Font.font("Times New Roman", 45));
		check.setOnAction(e -> {
			String choice = choices.getSelectedToggle().toString();
			choice = choice.substring(choice.indexOf("'") + 1, choice.length() - 1).trim();
			currentCorrect = currentQuiz.checkAnswer(choice, currentQuiz.getCurrentQuestion());
			windows.setScene(answerDisplayScene());

		});

		VBox nextChoice = new VBox(addMoreQuestion, check);
		nextChoice.setSpacing(250);
		nextChoice.setAlignment(Pos.CENTER);
		root.setRight(nextChoice);

		Scene scene = new Scene(root, 1000, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}

	private Scene answerDisplayScene() {
		BorderPane root = new BorderPane();
		Label status = new Label("answer page");
		status.setFont(Font.font("Times New Roman", 20));
		root.setTop(status);

		Label message = new Label();
		message.setText("Your Answer is: ");
		message.setFont(Font.font("Times New Roman", 40));
		message.setMaxHeight(100);

		// TextField filePath = new TextField();
		Label filePath = new Label();
		filePath.setFont(Font.font("American Typewriter", 50));
		filePath.setPrefSize(150, 80);
		filePath.setText("" + currentCorrect);
		// filePath.setEditable(false);

		// button2.setOnAction(e -> windows.setScene(buildScene0()));

		Button next = new Button("Next");
		next.setPrefSize(120, 80);
		next.setFont(Font.font("Times New Roman", 30));
		next.setAlignment(Pos.CENTER);
		next.setOnAction(e -> {
			if (currentQuiz.getAnswered() < currentQuiz.getAllQuestion()) {
				windows.setScene(quizAnsweringScene());
			} else if (currentQuiz.getAnswered() == currentQuiz.getAllQuestion()) {
				windows.setScene(scoreDisplayScene());
			}
		});
		HBox hbox = new HBox(message, filePath);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		root.setCenter(hbox);
		root.setRight(next);
		// root.setAlignment(hbox, Pos.CENTER);
		root.setAlignment(next, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(next, new javafx.geometry.Insets(100));

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

		Label numCorrect = new Label("Number of correct questions: " + currentQuiz.getCorrect());
		Label numAnswerd = new Label("Number of questions answers: " + currentQuiz.getAnswered());
		Label percentScore = new Label(
				"Percent correct: " + 100 * currentQuiz.getCorrect() / currentQuiz.getAnswered() + "%");
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

		Button moreQuestion = new Button("Add More Questions");
		moreQuestion.setFont(Font.font("Times New Roman", 20));
		moreQuestion.setPrefSize(200, 150);
		moreQuestion.setOnAction(e -> windows.setScene(quizImportScene()));
		Button exit = new Button("EXIT");
		exit.setFont(Font.font("Times New Roman", 30));
		exit.setPrefSize(200, 150);
		exit.setOnAction(e -> windows.setScene(saveQuizResult()));
		Button backHome = new Button("Back to HomePage");
		backHome.setPrefSize(200, 150);
		backHome.setFont(Font.font("Times New Roman", 20));
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
		prompt.setFont(Font.font("Times New Roman", 30));
		prompt.setAlignment(Pos.CENTER);

		Label notice = new Label("Enter fileName to save quiz: \n Please end in '.json'");
		notice.setPrefSize(320, 100);
		notice.setFont(Font.font("Times New Roman", 20));
		TextArea fileName = new TextArea();
		fileName.setPrefSize(250, 120);
		Button submit = new Button("Save");
		submit.setPrefSize(100, 80);
		submit.setFont(Font.font("Times New Roman", 30));
		submit.setAlignment(Pos.CENTER);
		submit.setOnAction(e -> {
			try (FileWriter file = new FileWriter(fileName.getText())) {
				currentQuiz.saveToJson(fileName.getText());
				Platform.exit();
			} catch (IOException ioe) {
				Alert badInputAlert = new Alert(AlertType.WARNING);
				badInputAlert.setTitle("Invalid input!");
				badInputAlert.setContentText("Please enter a valid file name to save the quiz");
				badInputAlert.setResizable(true);
				badInputAlert.showAndWait();
				windows.setScene(saveQuizResult());
			}	
		});

		VBox save = new VBox(notice, fileName, submit);
		save.setSpacing(5);

		// if user wants to exit, pop confirmation to the user
		Button exit = new Button("Exit without Save");
		exit.setFont(Font.font("Times New Roman", 25));
		exit.setPrefSize(250, 120);
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

		Button close = new Button("click here to close the window");
		close.setPrefSize(300, 80);
		close.setFont(Font.font("Times New Roman", 20));
		close.setAlignment(Pos.CENTER);
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
}
