package application;

import java.util.InputMismatchException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AddQuestion {

  Question question = null;
  int Choicecounter = 0;

  AddQuestion() {

  }

  public GridPane AddQuestionPage(QuestionBank questionBank) {
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

    Label metaLabel = new Label();
    metaLabel.setText("meta-data");
    metaLabel.setFont(Font.font("Times New Roman", 40));
    metaLabel.setMaxHeight(100);

    TextArea metaInput = new TextArea("Enter \n meta-data");
    metaInput.setWrapText(true);
    metaInput.setPrefSize(100, 50);
    metaInput.setFont(Font.font("Times New Roman", 20));
    VBox metaBox = new VBox(metaLabel, metaInput);
    root.add(metaBox, 1, 0);

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
    // EventHandler<ActionEvent> addHandler = new ManualAddQuestion(add);
    // everytime an action happens
    add.setOnAction(e -> {

      boolean badInput = false;

      question = new Question();

      String meta = metaInput.getText().trim();
      try {
        if (metaInput.getText() == null || metaInput.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {

          question.setMetadata(meta);


        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'meta' cannot be empty!,put \"unused\" if no meta-data input");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        metaInput.setStyle("-fx-text-box-border: red;");
        return;
      }

      String image = imageInput.getText().trim();
      try {
        if (imageInput.getText() == null || imageInput.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {

          question.setImagePath(image);


        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'image' cannot be empty!,put \"none\" if no image input");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        imageInput.setStyle("-fx-text-box-border: red;");
        return;
      }

      if (!image.equals("none")) {
        try {
          Image actualImage = new Image(image);
        } catch (Exception e1) {
          Alert badInputAlert = new Alert(AlertType.WARNING);
          badInputAlert.setTitle("Invalid input!");
          badInputAlert.setContentText("Invalid image path, put none for default picture");

          badInputAlert.setResizable(true);
          badInputAlert.showAndWait();
          return;

        }
      }
      String topic = topicInput.getText().trim();
      try {
        if (topicInput.getText() == null || topicInput.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {

          question.setTopic(topic);


        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'Topic' field! Must be a non-empty String!");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        topicInput.setStyle("-fx-text-box-border: red;");
        return;
      }

      String descContent = desInput.getText().trim();
      try {
        if (desInput.getText() == null || desInput.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {

          question.setDescription(descContent);


        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'Description' field! Must be a non-empty String!'none for not having this choice");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        desInput.setStyle("-fx-text-box-border: red;");
        return;
      }

      try {
        if (choiceInput1.getText().trim() == null || choiceInput1.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {
          if (!choiceInput1.getText().trim().equals("none")) {


            question.addChoices(new Choice(rb1.isSelected(), choiceInput1.getText().trim()));
            Choicecounter++;
          }
        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'Choice1' field! Must be a non-empty String!'none for not having this choice");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        choiceInput1.setStyle("-fx-text-box-border: red;");
        return;
      }
      try {
        if (choiceInput2.getText().trim() == null || choiceInput2.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {
          if (!choiceInput2.getText().trim().equals("none")) {
            question.addChoices(new Choice(rb2.isSelected(), choiceInput2.getText().trim()));
            Choicecounter++;
          }
        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'Choice2' field! Must be a non-empty String!'none for not having this choice");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        choiceInput2.setStyle("-fx-text-box-border: red;");
        return;
      }
      try {
        if (choiceInput3.getText().trim() == null || choiceInput3.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {
          if (!choiceInput3.getText().trim().equals("none")) {
            question.addChoices(new Choice(rb3.isSelected(), choiceInput3.getText().trim()));
            Choicecounter++;
          }
        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'Choice3' field! Must be a non-empty String!'none for not having this choice");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        choiceInput3.setStyle("-fx-text-box-border: red;");
        return;
      }
      try {
        if (choiceInput4.getText().trim() == null || choiceInput4.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {
          if (!choiceInput4.getText().trim().equals("none")) {
            question.addChoices(new Choice(rb4.isSelected(), choiceInput4.getText().trim()));
            Choicecounter++;
          }
        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on 'Choice4' field! Must be a non-empty String!,none for not having this choice");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        choiceInput4.setStyle("-fx-text-box-border: red;");
        return;
      }
      try {
        if (choiceInput5.getText().trim() == null || choiceInput5.getText().trim().equals("")) {
          throw new InputMismatchException();
        } else {
          if (!choiceInput5.getText().trim().equals("none")) {
            question.addChoices(new Choice(rb5.isSelected(), choiceInput5.getText().trim()));
            Choicecounter++;
          }
        }
      } catch (InputMismatchException e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert
            .setContentText("Invalid input on 'Choice5' field! Must be a non-empty String!'");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        choiceInput5.setStyle("-fx-text-box-border: red;");
        return;
      }
      try {
        if (Choicecounter < 2)
          throw new Exception();
      } catch (Exception e1) {
        badInput = true;
        Alert badInputAlert = new Alert(AlertType.WARNING);
        badInputAlert.setTitle("Invalid input!");
        badInputAlert.setContentText(
            "Invalid input on choices field! Must have at least two valid choices'");
        badInputAlert.setResizable(true);
        badInputAlert.showAndWait();
        // choiceInput5.setStyle("-fx-text-box-border: red;");
        return;

      }

      if (!badInput) {
  

        questionBank.addOneQuestion(question);
        
        
        Alert CorrectinputAlert = new Alert(AlertType.INFORMATION);
        CorrectinputAlert.setTitle("Success!");
        CorrectinputAlert.setContentText(
            "Question has been added to the Question Database");
        CorrectinputAlert.setResizable(true);
        CorrectinputAlert.showAndWait();
        // System.out.print(question.getTopic());

      }

    });
    return root;
  }

}
