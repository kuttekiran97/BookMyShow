package com.scaler.bookmyshowfeb23.controllers;

import com.scaler.bookmyshowfeb23.dtos.ResponseStatus;
import com.scaler.bookmyshowfeb23.dtos.SignUpRequestDto;
import com.scaler.bookmyshowfeb23.dtos.SignUpResponseDto;
import com.scaler.bookmyshowfeb23.models.User;
import com.scaler.bookmyshowfeb23.services.UserService;
import com.sun.net.httpserver.Authenticator;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    public SignUpResponseDto singUp(SignUpRequestDto requestDto) {
        SignUpResponseDto responseDto = new SignUpResponseDto();

        try {
            User user = userService.signUp(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
            responseDto.setUserId(user.getId());
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
