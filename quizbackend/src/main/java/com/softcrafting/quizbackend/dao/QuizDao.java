package com.softcrafting.quizbackend.dao;

import com.softcrafting.quizbackend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
