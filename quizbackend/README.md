# Quiz Game backend application

- springboot framework used for the project

- monolithic architecture 

- postgresql for the database 


## You can pull the and run the docker image for the postgresql
```
docker pull postgres
```
## configure and run it
```
docker run --name sqltutorial -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
```


## Creating the `question` Table
To create the `question` table in your database, run the following SQL command:

```sql
CREATE TABLE question (
id SERIAL PRIMARY KEY,
category VARCHAR(255) NOT NULL,
difficultylevel VARCHAR(50) NOT NULL,
option1 VARCHAR(255) NOT NULL,
option2 VARCHAR(255) NOT NULL,
option3 VARCHAR(255) NOT NULL,
option4 VARCHAR(255) NOT NULL,
questiontitle TEXT NOT NULL,
correctanswer VARCHAR(255) NOT NULL,
explanation TEXT,
tags VARCHAR(255)
);
```


### Explanation:
- The `sql` after the opening triple backticks specifies that the code block is SQL, which allows for syntax highlighting in some Markdown renderers (e.g., GitHub, Visual Studio Code).
- Ensure that the code block ends with another set of triple backticks.


```sql
INSERT INTO question (category, difficultylevel, option1, option2, option3, option4, questiontitle, correctanswer, explanation, tags)
VALUES
('Science', 'Medium', 'Cell', 'Atom', 'Molecule', 'Organ', 'What is the basic unit of life?', 'Cell', 'The cell is the smallest unit of life that can replicate independently.', 'biology,science'),

('Math', 'Hard', '2', '3', '4', '5', 'What is the square root of 16?', '4', 'The square root of 16 is 4.', 'mathematics,arithmetic'),

('Technology', 'Easy', 'Windows', 'Linux', 'MacOS', 'Android', 'Which operating system is developed by Microsoft?', 'Windows', 'Windows is developed and maintained by Microsoft.', 'computers,technology'),

('History', 'Medium', 'Alexander the Great', 'Julius Caesar', 'Napoleon Bonaparte', 'Cleopatra', 'Who was the first Roman emperor?', 'Julius Caesar', 'Although Julius Caesar was not an emperor, he laid the foundation for the Roman Empire.', 'history,leaders'),

('Geography', 'Easy', 'Nile', 'Amazon', 'Yangtze', 'Mississippi', 'Which is the longest river in the world?', 'Nile', 'The Nile River in Africa is generally considered the longest river in the world.', 'geography,water bodies'),

('Sports', 'Medium', 'Roger Federer', 'Rafael Nadal', 'Novak Djokovic', 'Serena Williams', 'Who holds the record for the most Wimbledon titles in men''s tennis?', 'Roger Federer', 'Roger Federer has won the most Wimbledon titles in men''s singles.', 'sports,tennis'),

('Literature', 'Hard', 'The Great Gatsby', 'Moby Dick', 'Pride and Prejudice', 'To Kill a Mockingbird', 'Which book was written by F. Scott Fitzgerald?', 'The Great Gatsby', 'F. Scott Fitzgerald authored "The Great Gatsby" in 1925.', 'books,authors'),

('Science', 'Easy', 'Mars', 'Jupiter', 'Saturn', 'Neptune', 'Which planet is known as the Red Planet?', 'Mars', 'Mars is known as the Red Planet due to its reddish appearance.', 'astronomy,planets'),

('Movies', 'Medium', 'The Dark Knight', 'Inception', 'Dunkirk', 'Memento', 'Which movie features the character "Joker" played by Heath Ledger?', 'The Dark Knight', 'Heath Ledger won a posthumous Academy Award for his portrayal of the Joker in "The Dark Knight".', 'cinema,actors'),

('General Knowledge', 'Hard', 'Albert Einstein', 'Isaac Newton', 'Galileo Galilei', 'Nikola Tesla', 'Who developed the theory of general relativity?', 'Albert Einstein', 'Albert Einstein is best known for developing the theory of general relativity.', 'science,physics');
```