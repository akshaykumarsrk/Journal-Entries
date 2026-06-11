package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getAllUsers(){
        List<User> users = userService.getAllUser();
        return ResponseEntity
                .ok()
                .body(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity getUserByUsername(@PathVariable("username") String username){
        User user = userService.getUser(username);
        return ResponseEntity
                .ok()
                .body(user);
    }

    @PutMapping("/update-user")
    public ResponseEntity updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.updateUser(username, user);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("User Updated Successfully");
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUser(username);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("User Deleted Successfully");
    }


}