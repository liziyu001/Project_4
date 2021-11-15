
You have to compile all the classes created (Account, Course, Manager, Question, Quiz, Submission, ManagementSystem), and you only have to run the ManagementSystem class because that is the only class that contains a main method. You have to compile all the classes though because methods in other classes are implemented in the ManagementSystem class.   

Ram Laxminarayan - Submitted Report on Brightspace. William Li - Submitted Vocareum workspace.

The Account class represents the account of either a teacher or a student, and it intakes methods that a teacher can use to make quizzes, and student can use to take the quizzes. 
The Course class represents the course that the teacher created and the student is taking. This relates to the Account class because based on if the account is a teacher or student count, that can determine if one can create/edit or do anything with the course, or just take the course. It contains the toString() of quiz and question, and also adds the course name. 
The Question class represents the questions that are going to appear on the quiz, as it takes a prompt, answer choices, and even the correct answer if required. 
The Quiz class represents the quiz where it includes a list of questions that can be created from adding Question objects, and also adds the name of the quiz. The quiz implements the prompt, answer choice, and correct answer, if necessary, from the Question object. 
The Submission class represents the student's submission that is going to be graded by the teacher. This class relates to other classes in the project because a teacher with a teacher account can grade the submission of each question in the quiz of a specific course, as the gradeSubmission method was created in the Account class. 
The Manager class is the main thing that reads and writes through files, and updates the account, course, question, quiz, and submission based on the files, and also based on the other classes/object updates, as it incorporates all the non-main method classes, and updates/changes or manages them. 
The ManagementSystem class is where the main method is, and is the class where you test everything, as the ManagementSystem class determines the functionality of all the other classes, and determines if the methods are doing what it is supposed to be doing based on the comments described for that method.
All the classes are tested through the ManagementSystem class, as the classes work as intended if the methods made in that specific class work in the main method in the ManagementSystem class. 

