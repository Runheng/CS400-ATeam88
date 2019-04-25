package application;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class CurrentQuiz {

  private int numberOfCurrentQuestion = 0;
  private int numberOfQuestionAnswered = 0;

  private int numberOfCorrect = 0;

  public ArrayList<Question> currentQuizList;

  public int currentQuestion = 0;
  public Random rand;
  public Question displayingQuestion;


  CurrentQuiz(int scopeSize, List<Question> subjectQuestions) {
    rand = new Random();
    currentQuizList = new ArrayList<Question>();

    numberOfCurrentQuestion =
        scopeSize <= subjectQuestions.size() ? scopeSize : subjectQuestions.size();


    while (currentQuizList.size() < numberOfCurrentQuestion) {
      int index = rand.nextInt(subjectQuestions.size());

      if (!currentQuizList.contains(subjectQuestions.get(index))) {
        currentQuizList.add(subjectQuestions.get(index));
      }

    }
  }

  public boolean checkAnswer(String userAnswer, Question question) {


    if (userAnswer.equals(question.getAnswer())) {
      ++numberOfCorrect;
      ++numberOfQuestionAnswered;
      ++currentQuestion;

      return true;
    } else {
      ++currentQuestion;
      ++numberOfQuestionAnswered;
      return false;
    }
  }

  public Question getCurrentQuestion() {
    return currentQuizList.get(currentQuestion);
  }

  public int getAnswered() {
    return numberOfQuestionAnswered;
  }

  public int getAllQuestion() {
    return numberOfCurrentQuestion;
  }

  public int getCorrect() {
    return numberOfCorrect;
  }


  @SuppressWarnings("unchecked")
  public void saveToJson(String fileName) {
    JSONArray questions = new JSONArray();
    for (int i = 0; i < numberOfCurrentQuestion; i++) {
      JSONObject singleQues = new JSONObject();
      singleQues.put("meta-data", currentQuizList.get(i).getMetadata());
      singleQues.put("questionText", currentQuizList.get(i).getDescription());
      singleQues.put("topic", currentQuizList.get(i).getTopic());
      singleQues.put("image", currentQuizList.get(i).getImagePath());
      // add choices here,TODO
      JSONArray choices = new JSONArray();
      for (int k = 0; k < currentQuizList.get(i).getChoices().size(); k++) {
        JSONObject singleChoice = new JSONObject();
        boolean trueOrFalse = currentQuizList.get(i).getChoices().get(k).getAnswer();
        if (trueOrFalse == true) {
          singleChoice.put("isCorrect", "T");
        } else {
          singleChoice.put("isCorrect", "F");
        }
        singleChoice.put("choice", currentQuizList.get(i).getChoices().get(k).getContent());
        choices.add(singleChoice);
      }
      singleQues.put("choiceArray", choices);
      questions.add(singleQues);
    }
    JSONObject done = new JSONObject();
    done.put("questionArray", questions);
    try (FileWriter file = new FileWriter(fileName)) {
      file.write(done.toJSONString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


}
