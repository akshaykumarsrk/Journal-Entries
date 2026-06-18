package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArguementsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().username("shyam1").password("shyam1").build()),
                Arguments.of(User.builder().username("suraj1").password("suraj1").build())
        );
    }
}
