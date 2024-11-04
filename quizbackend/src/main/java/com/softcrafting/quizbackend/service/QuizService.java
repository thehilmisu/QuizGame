package com.softcrafting.quizbackend.service;

import com.softcrafting.quizbackend.dao.QuestionDao;
import com.softcrafting.quizbackend.dao.QuizDao;
import com.softcrafting.quizbackend.model.Question;
import com.softcrafting.quizbackend.model.QuestionWrapper;
import com.softcrafting.quizbackend.model.Quiz;
import com.softcrafting.quizbackend.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            if (category == null || category.isEmpty()) {
                return new ResponseEntity<>("Category cannot be null or empty", HttpStatus.BAD_REQUEST);
            }
            if (title == null || title.isEmpty()) {
                return new ResponseEntity<>("Title cannot be null or empty", HttpStatus.BAD_REQUEST);
            }
            if (numQ <= 0) {
                return new ResponseEntity<>("Number of questions must be greater than 0", HttpStatus.BAD_REQUEST);
            }

            List<Question> questions = questionDao.findRandomQuestionByCategory(category, numQ);

            if (questions.size() < numQ) {
                return new ResponseEntity<>("Not enough questions available for the given category", HttpStatus.BAD_REQUEST);
            }

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizDao.save(quiz);

            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);

        } catch (Exception e) {
            // Log the exception (use a logging framework in real applications)
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while creating the quiz", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        try {
            Optional<Quiz> quizOpt = quizDao.findById(id);

            if (quizOpt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Question> questionsFromDB = quizOpt.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for(Question q : questionsFromDB){
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4(), q.getQuestiontitle());
                questionsForUser.add(qw);
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<UserResponse> responses) {
        try {
            Optional<Quiz> quizOpt = quizDao.findById(id);

            if (quizOpt.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Quiz quiz = quizOpt.get();
            List<Question> questions = quiz.getQuestions();
            int score = 0;

            for (int i = 0; i < responses.size(); i++) {
                if (i < questions.size() && responses.get(i).getResponse().equals(questions.get(i).getCorrectanswer())) {
                    score++;
                }
            }
            return new ResponseEntity<>(score, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
