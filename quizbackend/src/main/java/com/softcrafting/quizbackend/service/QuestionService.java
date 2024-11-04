package com.softcrafting.quizbackend.service;

import com.softcrafting.quizbackend.dao.QuestionDao;
import com.softcrafting.quizbackend.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findQuestionByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        //TODO: handle the exception here
        questionDao.save(question);
        return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        //TODO: handle the exception here
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        try {
            Optional<Question> existingQuestionOpt = questionDao.findById(id);

            if (existingQuestionOpt.isPresent()) {
                Question existingQuestion = existingQuestionOpt.get();

                existingQuestion.setCategory(question.getCategory());
                existingQuestion.setDifficultylevel(question.getDifficultylevel());
                existingQuestion.setOption1(question.getOption1());
                existingQuestion.setOption2(question.getOption2());
                existingQuestion.setOption3(question.getOption3());
                existingQuestion.setOption4(question.getOption4());
                existingQuestion.setQuestiontitle(question.getQuestiontitle());
                existingQuestion.setCorrectanswer(question.getCorrectanswer());
                existingQuestion.setExplanation(question.getExplanation());
                existingQuestion.setTags(question.getTags());

                questionDao.save(existingQuestion);

                return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
