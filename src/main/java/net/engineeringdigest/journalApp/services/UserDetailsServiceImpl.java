package net.engineeringdigest.journalApp.services;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

        // Convert our User into UserDetails
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))   // Internally, it behaves something like: String[] result = new String[roles.size()]; and copies all elements.
                .build();

        return userDetails;

    }
}


// String[] arr = roles.toArray(new String[0]);
// Step 1- Java receives: new String[0] which is: [] (length = 0)
// Step 2- Inside Java's toArray() implementation, it checks:
// if (givenArray.length < list.size())
// In our case: 0 < 2 ✅ True
// So Java says: "The array you gave me is too small."
// Step 3- Java automatically creates a NEW array of the correct size.
// Something like: String[] newArray = new String[2];
// Now: [ null, null ]
// Step 4- Java copies elements from the List:
// newArray[0] = "USER";
// newArray[1] = "ADMIN";
// Now: ["USER", "ADMIN"]
// Step 5- Java returns this newly created array:
// return newArray;
// So: String[] arr = roles.toArray(new String[0]);
// becomes:
// arr
// └──> ["USER", "ADMIN"]
// The original empty array is discarded.


// Simplified Internal Code
// Think of toArray() as doing this:

// public String[] myToArray(List<String> list, String[] array) {
//
//    if(array.length < list.size()) {
//        array = new String[list.size()];
//    }
//
//    for(int i = 0; i < list.size(); i++) {
//        array[i] = list.get(i);
//    }
//
//    return array;
// }

// myToArray(List.of("USER","ADMIN"), new String[0]);