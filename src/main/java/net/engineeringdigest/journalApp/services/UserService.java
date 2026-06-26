package net.engineeringdigest.journalApp.services;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean saveNewUser(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            User saveUser = userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            log.error("Error occurred for {}: ", user.getUsername(), e);
            log.info("Error occurred for {}: ", user.getUsername(), e);
            log.debug(e.getMessage());
            log.trace(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public User saveUser(User user) {
        return userRepository.save(user);
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

    public User createAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        User saveUser = userRepository.save(user);
        return saveUser;
    }
}
