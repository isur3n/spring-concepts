package me.suren.springconcepts.app.controller;

import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.annotation.TimeIt;
import me.suren.springconcepts.app.dto.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/app/spring-concepts/user")
public class UserController {

    @TimeIt
    @PostMapping({"", "/"})
    public User getRandomNumber(@Validated @RequestBody User user) {

        return user;
    }
}
