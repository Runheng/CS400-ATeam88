README

Course: cs400
Semester: Spring 2019
Project name: Quiz Generator
Team Members:
1. Jiaqi Wu, lec 004, jwu359@wisc.edu
2. Runheng Lei, lec 004, rlei5@wisc.edu
3. Yuchen Liu, lec 004, yliu687@wisc.edu
4. Ran Meng, lec 004, rmeng8@wisc.edu


Notes or comments to the grader: 

We implement the quiz generator by constructing several scenes, so that each scene has its unique function, while they all together could fullfill all the requests. 

The start scene enables the user to choose between add questions to database or take a quiz, and pops up a warning when the user try to start a quiz with an empty question bank. So we add total question number at the top of the scene to remind the user how many questions exist in database.

The import scene enables the user to either import a JSON file or add a single question. Warnings will pop to the user if the file path is invalid, or the image needed in the question is not provided.

The construct scene is the page that the user has to fill out all required info to generate a single question, an invalid input will cause a pop-up warning and a success notice is showed when user click on submit, and all required data are filled in.

The selection page asks the user to choose the topic and num of questions in the quiz. Invalid input will also cause a pop-up warning.

The answering scene is composed of a single question, with question description, question topic, answer choices, question image, num of question in total, and num of questions answered showed in an uniform format. The check button will lead the user to the result scene.

The result scene tells the user if the choice chose is correct or not.

The score scene is the feedback on the current quiz, so that the user knows how many questions they got correct, etc, and the user can also choose among add more questions, go to homepage (start scene), or to exit the app.

The save scene asks the user if he/she wants to save the quiz to a file, if so the user has to enter a valid filename (or warnings will pop up) and click on save to download the file and exit the program, if not, the user will see a confirmation and exit the app when clicking OK.

The close scene shows a goodbye message, and the user can exit the app by clicking on the button in this page.



