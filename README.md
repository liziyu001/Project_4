
You have to compile all the classes created (Account, Course, Manager, Question, Quiz, Submission, ManagementSystem), and you only have to run the ManagementSystem class because that is the only class that contains a main method. You have to compile all the classes though because methods in other classes are implemented in the ManagementSystem class.   

File System + formats:

//When a course is created, a coursename.txt file is made with an empty course and its name is added to the courses.txt file
sampleCourse.txt:
CourseName: sampleCourse
_____________________________________
courses.txt:
sampleCourse
_____________________________________
//When a quiz is created, its contents are added to the course object that it is under, and the coursesname.txt file is updated
// THE CORRECT ANSWER REPRESENTS THE INDEX OF THE CORRECT ANSWER
_____________________________________
sampleCourse.txt:
CourseName: sampleCourse
isRandom: false
Name of Quiz: Q1
Prompt of Question: What is 1 + 1?
1. 2
2. 0
3. 1
Correct Answer: 0
Prompt of Question: What is 2 * 2?
1. 2
2. 4
3. 0
4. 8
Correct Answer: 1
_____________________________________
//a file with the format: coursename_quizname_timestamp.txt is also created, with timestamp = time of quiz creation
//it initially has the contents of the quiz object
______________________________________
sampleCourse_Q1_2021.11.15.05.00.00.txt:
isRandom: false
Name of Quiz: Q1
Prompt of Question: What is 1 + 1?
1. 2
2. 0
3. 1
Correct Answer: 0
Prompt of Question: What is 2 * 2?
1. 2
2. 4
3. 0
4. 8
Correct Answer: 1
____________________________________
//When a new quiz is created, the filename is added to the accessible_courses.txt file
______________________________________
accessible_courses.txt:
sampleCourse_Q1_2021.11.15.05.00.00.txt
______________________________________
//When a student takes a quiz, their submission is added to the coursename_quizname_timestamp.txt file
_____________________________________
sampleCourse_Q1_2021.11.15.05.00.00.txt:
isRandom: false
Name of Quiz: Q1
Prompt of Question: What is 1 + 1?
1. 2
2. 0
3. 1
Correct Answer: 0
Prompt of Question: What is 2 * 2?
1. 2
2. 4
3. 0
4. 8
Correct Answer: 1

Submission: 
Username: leo
Graded: false
Answers: [1, 2]
Points: [0, 0]
Total Points: 0
Time Submitted: 2021.11.15 09-10-04
___________________________________
//When the teacher grades, submissions, they can edit this file and assign point values to the answers
___________________________________
sampleCourse_Q1_2021.11.15.05.00.00.txt:
isRandom: false
Name of Quiz: Q1
Prompt of Question: What is 1 + 1?
1. 2
2. 0
3. 1
Correct Answer: 0
Prompt of Question: What is 2 * 2?
1. 2
2. 4
3. 0
4. 8
Correct Answer: 1

Submission: 
Username: leo
Graded: true
Answers: [1, 2]
Points: [10, 10]
Total Points: 20
Time Submitted: 2021.11.15 09-10-04
___________________________________
//When a student wants to submit a file as an answer to a quiz, it must contain only 1 integer
//When a teacher wants to use a file to create a quiz, it must contain only 1 quiz in the format shown above
//When a teacher deletes or edits a quiz, the quiz is removed from the accessable_quizzes.txt file, and students can't view them anymore
//When a user deletes thier account or edits their account's username, the username is added to a file called "deleted_accounts.txt"
//Users can't change their name or create accounts with the same name as any name in this file
Ram Laxminarayan - Submitted Report on Brightspace. William Li - Submitted Vocareum workspace.

The Account class represents the account of either a teacher or a student, and it intakes methods that a teacher can use to make quizzes, and student can use to take the quizzes. 
The Course class represents the course that the teacher created and the student is taking. This relates to the Account class because based on if the account is a teacher or student count, that can determine if one can create/edit or do anything with the course, or just take the course. It contains the toString() of quiz and question, and also adds the course name. 
The Question class represents the questions that are going to appear on the quiz, as it takes a prompt, answer choices, and even the correct answer if required. 
The Quiz class represents the quiz where it includes a list of questions that can be created from adding Question objects, and also adds the name of the quiz. The quiz implements the prompt, answer choice, and correct answer, if necessary, from the Question object. 
The Submission class represents the student's submission that is going to be graded by the teacher. This class relates to other classes in the project because a teacher with a teacher account can grade the submission of each question in the quiz of a specific course, as the gradeSubmission method was created in the Account class. 
The Manager class is the main thing that reads and writes through files, and updates the account, course, question, quiz, and submission based on the files, and also based on the other classes/object updates, as it incorporates all the non-main method classes, and updates/changes or manages them. 
The ManagementSystem class is where the main method is, and is the class where you test everything, as the ManagementSystem class determines the functionality of all the other classes, and determines if the methods are doing what it is supposed to be doing based on the comments described for that method.
All the classes are tested through the ManagementSystem class, as the classes work as intended if the methods made in that specific class work in the main method in the ManagementSystem class. 

