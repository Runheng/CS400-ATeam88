package application;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class QuizAnswering {
	ToggleGroup tg = new ToggleGroup();

	public BorderPane quizAnswering(CurrentQuiz currentQuiz) {
		Question question = currentQuiz.getCurrentQuestion();
		BorderPane root = new BorderPane();
		Label pageLabel = new Label();
		pageLabel.setText("Quiz In Progress");
		pageLabel.setFont(Font.font("Times New Roman", 30));
		pageLabel.setMaxHeight(80);

		TextArea des = new TextArea();
		des.setText(question.getDescription());
		des.setWrapText(true);
		des.setEditable(false);
		des.setFont(Font.font("Times New Roman", 30));
		des.setMaxHeight(80);
		VBox desc = new VBox(pageLabel, des);
		root.setTop(desc);

		Image image = question.getImagePath().equals("none") ? new Image("defaultQuestionBackground.jpg")
				: new Image(question.getImagePath());
		ImageView questionPic = new ImageView(image);
		questionPic.setFitHeight(350);
		questionPic.setPreserveRatio(true);
		root.setCenter(questionPic);

		Label topicLabel = new Label("Current Topic");
		topicLabel.setPrefSize(140, 90);
		topicLabel.setFont(Font.font("Times New Roman", 20));
		TextArea topicLabel1 = new TextArea(question.getTopic());
		topicLabel1.setWrapText(true);
		topicLabel1.setEditable(false);
		topicLabel1.setPrefSize(150, 90);
		topicLabel1.setFont(Font.font("Times New Roman", 30));
		Label quesAns = new Label("Answered: " + currentQuiz.getAnswered());
		quesAns.setPrefSize(140, 90);
		quesAns.setFont(Font.font("Times New Roman", 20));
		Label quesAll = new Label("Total Question: " + currentQuiz.getAllQuestion());
		quesAll.setPrefSize(140, 90);
		quesAll.setFont(Font.font("Times New Roman", 20));
		VBox condition = new VBox(topicLabel, topicLabel1, quesAns, quesAll);
		condition.setSpacing(40);
		condition.setAlignment(Pos.CENTER);
		root.setLeft(condition);

		tg = new ToggleGroup();
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		ArrayList<Choice> choices = question.getChoices();
		for (int i = 0; i < choices.size(); i++) {
			Choice current = choices.get(i);

			RadioButton rb = new RadioButton(current.getContent());
			rb.setPrefSize(400, 50);
			rb.setFont(Font.font("Times New Roman", 20));
			rb.setToggleGroup(tg);
			rb.setSelected(true);

			vbox.getChildren().add(rb);
		}

		Label answerList = new Label("Answer: ");
		answerList.setFont(Font.font("Times New Roman", 30));
		answerList.setPrefSize(150, 100);
		answerList.setAlignment(Pos.CENTER);

		HBox hbox = new HBox(answerList, vbox);
		root.setBottom(hbox);

		BackgroundImage myBI = new BackgroundImage(new Image("background.jpg", 1000, 800, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, BackgroundSize.DEFAULT);
		root.setBackground(new Background(myBI));
		return root;

	}

	public ToggleGroup getToggleGroup() {

		return tg;
	}
}
