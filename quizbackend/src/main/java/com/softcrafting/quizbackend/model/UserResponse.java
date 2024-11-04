package com.softcrafting.quizbackend.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse {
    private Integer id;
    private String response;
}
