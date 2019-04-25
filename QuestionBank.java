package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class QuestionBank {

	private List<String> topicList;
	private HashMap<String, List<Question>> bank;
	private int numQuestion;

	// default no-arg constructor
	public QuestionBank() {
		topicList = new ArrayList<String>();
		bank = new HashMap<String, List<Question>>();
		numQuestion = 0;
	}

	public void addQuestionFile(String filepath) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		Object obj = null;

		obj = parser.parse(new FileReader(filepath));

		JSONObject jo = (JSONObject) obj;
		JSONArray question = (JSONArray) jo.get("questionArray");
		for (int i = 0; i < question.size(); i++) {
			JSONObject current = (JSONObject) question.get(i);
			String description = (String) current.get("questionText");
			String topic = (String) current.get("topic");
			String imagepath = (String) current.get("image");
			String metadata = (String) current.get("meta-data");
			JSONArray answers = (JSONArray) current.get("choiceArray");
			List<Choice> allChoices = new ArrayList<Choice>();
			if (answers != null) {
				for (int k = 0; k < answers.size(); k++) {
					JSONObject answerInfo = (JSONObject) answers.get(k);
					String result = (String) answerInfo.get("isCorrect");
					boolean trueOrFalse = false;
					if (result.equals("T")) {
						trueOrFalse = true;
					} else if (result.equals("F")) {
						trueOrFalse = false;
					}
					String answerLine = (String) answerInfo.get("choice");
					allChoices.add(new Choice(trueOrFalse, answerLine));
				}
			}
			// will add the one that can be add
			// and skip the question that has image path error
			if (!imagepath.equals("none")) {
				try {
					@SuppressWarnings("unused")
					Image image = new Image(imagepath);
				} catch (Exception e) {
					Alert badInputAlert = new Alert(AlertType.WARNING);
					badInputAlert.setTitle("Invalid input!");
					badInputAlert.setContentText("Invalid image path");
					badInputAlert.setResizable(true);
					badInputAlert.showAndWait();
					continue;
				}
			}
			Question newQuestion = new Question();
			newQuestion.setDescription(description);
			newQuestion.setMetadata(metadata);
			newQuestion.setTopic(topic);
			newQuestion.setImagePath(imagepath);
			newQuestion.addAllChoices((ArrayList<Choice>) allChoices);
			addOneQuestion(newQuestion);
		}
	}

	public void addOneQuestion(Question q) {
		String subject = q.getTopic();

		if (!topicList.contains(subject)) {
			addTopicInOrder(topicList, subject);

			bank.put(subject, new ArrayList<Question>());

		}
		bank.get(subject).add(q);
		numQuestion++;
	}

	private void addTopicInOrder(List<String> list, String topic) {
		// loop through all elements
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).compareTo(topic) < 0)
				continue;

			list.add(i, topic);
			return;
		}

		list.add(topic);
	}

	public List<String> getTopicList() {
		return topicList;
	}

	public List<Question> getQuestionInOneTopic(String topic) {
		return bank.get(topic);
	}

	public int getNumQuestion() {
		return numQuestion;
	}
}
