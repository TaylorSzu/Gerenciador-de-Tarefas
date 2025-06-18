package com.saep.taylorsz.Controller;

import com.saep.taylorsz.DTO.UserDTO;
import com.saep.taylorsz.Models.User;
import com.saep.taylorsz.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody User user){
        try {
            User createUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(user);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list(){
        try {
            List<UserDTO> users = userService.listAll();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
}
