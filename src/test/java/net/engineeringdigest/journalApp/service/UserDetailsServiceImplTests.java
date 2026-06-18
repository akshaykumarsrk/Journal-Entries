package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

// JUnit doesn't understand Mockito automatically.
// tells JUnit: "Before running tests, initialize Mockito annotations."
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTests {

    // Mockito creates a fake UserRepository.
    @Mock
    private UserRepository userRepository;

    // Normally Spring injects it. During testing Spring is not running. So Mockito does the injection as well as create:
    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Test
    void loadUserByUsernameTest() {
        // Now Mockito says: "Whenever somebody calls findByUsername(), don't go to MongoDB. Just return this fake user."
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(
                        User.builder()
                                .username("akshay")
                                .password("akshay")
                                .roles(List.of("USER"))
                                .build()
                ));

        UserDetails user =
                userDetailsServiceImpl.loadUserByUsername("akshay");

        Assertions.assertNotNull(user);
    }
}

// Use Mockito (@Mock, @InjectMocks) when: I want to test only one class.
// eg: UserService
// UserDetailsService
// JournalService
// Use @SpringBootTest when: I want to test the whole Spring application.
// eg: Security
// Database
// Controller
// Repository
// Bean Wiring