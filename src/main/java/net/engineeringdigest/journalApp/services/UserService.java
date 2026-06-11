package net.engineeringdigest.journalApp.services;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(User user) {

        String password = user.getPassword();
        password = passwordEncoder.encode(password);
        user.setPassword(password);
        user.setRoles(Arrays.asList("USER"));
        User saveUser = userRepository.save(user);
        return saveUser;
    }

    public User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        return user;
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateUser(String username, User newUser) {
        User oldUser = findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));

        if(oldUser != null){
            // update
            oldUser.setUsername(newUser.getUsername());
            oldUser.setPassword(newUser.getPassword());
            saveUser(oldUser);
        }
    }
}
