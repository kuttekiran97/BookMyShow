package com.scaler.bookmyshowfeb23;

import com.scaler.bookmyshowfeb23.controllers.UserController;
import com.scaler.bookmyshowfeb23.dtos.SignUpRequestDto;
import com.scaler.bookmyshowfeb23.dtos.SignUpResponseDto;
import com.scaler.bookmyshowfeb23.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowFeb23Application implements CommandLineRunner  {
    @Autowired
    private UserController userController;

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowFeb23Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SignUpRequestDto requestDto = new SignUpRequestDto();
        requestDto.setEmail("umesh@scaler.com");
        requestDto.setPassword("password");

        SignUpResponseDto responseDto = userController.singUp(requestDto);
        System.out.println("DEBUG");
    }
}
