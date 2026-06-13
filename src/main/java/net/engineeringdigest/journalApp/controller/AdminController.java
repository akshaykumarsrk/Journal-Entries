package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUser();
        if (users != null && !users.isEmpty()) {
            return new ResponseEntity(users, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity createAdminUser(@RequestBody User user) {
        User admin = userService.createAdminUser(user);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
