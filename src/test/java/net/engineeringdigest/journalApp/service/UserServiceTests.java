package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSaveNewUser() {
        User user = User.builder()
                .username("akshay")
                .password("secret")
                .roles(new ArrayList<>())
                .build();

        when(passwordEncoder.encode("secret")).thenReturn("encoded-secret");
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertTrue(userService.saveNewUser(user));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testNumber(int number) {

        assertTrue(number > 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Akshay",
            "Java",
            "Spring"
    })
    void testString(String value) {

        assertNotNull(value);
    }

    @ParameterizedTest
    @NullSource
    void testNull(String value) {

        assertNull(value);
    }

    @ParameterizedTest
    @EmptySource
    void testEmpty(String value) {

        assertTrue(value.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testData(String value) {

        assertTrue(
                value == null ||
                        value.isEmpty()
        );
    }
}
