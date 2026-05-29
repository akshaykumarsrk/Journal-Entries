package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry){
        JournalEntry entry = journalEntryRepository.save(journalEntry);
        return entry;
    }

    public JournalEntry getEntry(String id){
        Optional<JournalEntry> optional = journalEntryRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public List<JournalEntry> getEntries(){
        return journalEntryRepository.findAll();
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public JournalEntry deleteEntry(String myId) {
        Optional<JournalEntry> optional = journalEntryRepository.findById(myId);
        if(optional.isPresent()){
            JournalEntry journalEntry = optional.get();
            journalEntryRepository.delete(journalEntry);
        }
        return journalEntryRepository.findById(myId).get();
    }

    public JournalEntry updateEntry(String myId, JournalEntry newEntry) {
        Optional<JournalEntry> optional = journalEntryRepository.findById(myId);
        if(optional.isPresent()){
            JournalEntry oldEntry = optional.get();
            oldEntry.setTitle(newEntry.getTitle());
            oldEntry.setContent(newEntry.getContent());
            oldEntry.setDate(LocalDateTime.now());
            return journalEntryRepository.save(oldEntry);
        }
        return null;
    }
}