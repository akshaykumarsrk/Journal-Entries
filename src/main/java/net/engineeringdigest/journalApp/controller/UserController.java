package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity getUserByUsername(@PathVariable("username") String username){
        User user = userService.getUser(username);
        return ResponseEntity
                .ok()
                .body(user);
    }

    @PutMapping("/username/{usermane}")
    public ResponseEntity updateUser(@PathVariable String usermane, @RequestBody User user){
        userService.updateUser(usermane, user);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("User Updated Successfully");
    }

    @DeleteMapping("/delete/{myId}")
    public ResponseEntity deleteUser(@PathVariable ObjectId myId){
        userService.deleteUser(myId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("User Deleted Successfully");
    }
}