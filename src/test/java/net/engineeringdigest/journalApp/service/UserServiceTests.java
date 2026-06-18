package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.services.UserService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ArgumentsSource(UserArguementsProvider.class)
    public void testSaveNewUser(User user) {
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
