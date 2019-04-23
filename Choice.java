package application;

/**
 * Choice class
 * @author Jiaqi Wu
 *
 */
public class Choice {
  private boolean answer;
  private String content;


  Choice(boolean answer, String content) {
    this.answer = answer;
    this.content = content;

  }

  public void setAnswer(boolean answer) {
    this.answer = answer;

  }

  public void setContent(String content) {
    this.content = content;

  }

  public boolean getAnswer() {
    return answer;

  }

  public String getContent() {
    return content;
  }
}
