package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.JournalEntryNotFoundException;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    // it tells spring either save transaction in both user as well as journalentry table or dont save anywhere
    // it makes entry atomic and isolated (ACID Properties)
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));

        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(journalEntry);
//        user.setUsername(null);
        userService.saveUser(user);
    }

    public List<JournalEntry> getAllJournalEntriesOfUser(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        List<JournalEntry> journalEntries = user.getJournalEntries();
        return journalEntries;
    }

    public JournalEntry getJournalEntryById(User user, ObjectId id) {

        List<JournalEntry> entries = user.getJournalEntries();
        if(entries != null && !entries.isEmpty())
        {
            for(JournalEntry journalEntry : entries)
            {
                ObjectId entryId = journalEntry.getId();
                if(entryId.equals(id))
                {
                    return journalEntry;
                }
            }
        }
        return null;
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public void deleteEntry(ObjectId myId, String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        List<JournalEntry> entries = user.getJournalEntries();
        if(entries != null && !entries.isEmpty()){
            journalEntryRepository.deleteById(myId);
            entries.remove(myId);
        }
        userService.saveUser(user);
    }

    public void updateEntry(ObjectId myId, String username, JournalEntry newEntry) {
        JournalEntry oldEntry = journalEntryRepository.findById(myId)
                .orElseThrow(() -> new JournalEntryNotFoundException("Invalid JournalEntry Id"));
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));

        oldEntry.setDate(LocalDateTime.now());
        oldEntry.setTitle(newEntry.getTitle());
        oldEntry.setContent(newEntry.getContent());
        journalEntryRepository.save(oldEntry);

    }

//    public List<JournalEntry> findByUsername(String username) {
//
//    }
}