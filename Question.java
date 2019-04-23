package application;

import java.util.ArrayList;

public class Question {
  String description;
  ArrayList<Choice> choices;
  String topic;
  String imagePath;

  Question(String topic, String imagePath) {
    this.topic = topic;
    this.imagePath = imagePath;
  }

  public void addChoices(Choice choice) {

    choices.add(choice);
  }

  public void setDescription(String des) {

    this.description = des;
  }


  public String getDescription() {

    return this.description;
  }

  public void setTopic(String topic) {

    this.topic = topic;
  }

  public String getTopic() {

    return this.topic;
  }

  public void setImagePath(String path) {

    this.imagePath = path;
  }

  public String getImagePath() {

    return this.imagePath;
  }

  /**
   * @return a choice for correct answer, return null for no current answer possible
   */
  public Choice getAnswer() {
    for (Choice i : choices) {
      if (i.getAnswer() == true)
        return i;
    }
    return null;
  }



}
