package application;

import java.io.File;
import java.util.ArrayList;

public class Question {
  private String description;
  private ArrayList<Choice> choices;
  private String topic;
  private String imagePath;
  private File file;
  private String metadata;


  Question() {
   
    this.choices = new ArrayList<Choice>();
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
  public String getAnswer() {
    for (Choice i : choices) {
      if (i.getAnswer() == true)
        return i.getContent();
    }
    return null;
  }

  public void addAllChoices(ArrayList<Choice> allChoices) {
    this.choices = allChoices;
  }

  public void setMetadata(String metadata) {
    this.metadata = metadata;
  }

  public String getMetadata() {
    return this.metadata;
  }

  public ArrayList<Choice> getChoices() {
    return this.choices;
  }
}
