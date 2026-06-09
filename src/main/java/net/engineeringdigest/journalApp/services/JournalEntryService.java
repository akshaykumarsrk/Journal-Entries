package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.JournalEntryNotFoundException;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
//        user.setUsername(null);
        userService.saveUser(user);
    }

    public List<JournalEntry> getAllJournalEntriesOfUser(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        List<JournalEntry> journalEntries = user.getJournalEntries();
        return journalEntries;
    }

    public JournalEntry getJournalEntryById(ObjectId id) {
        JournalEntry journalEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new JournalEntryNotFoundException("Invalid JournalEntry Id"));
        return journalEntry;
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public void deleteEntry(ObjectId myId, String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        user.getJournalEntries().remove(getJournalEntryById(myId));
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
}