package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal/v2")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;


    @GetMapping("{username}")
    public ResponseEntity getAllJournalEntriesOfUser(@PathVariable String username)
    {
        List<JournalEntry> entries = journalEntryService.getAllJournalEntriesOfUser(username);
        if(entries != null && !entries.isEmpty())
            return new ResponseEntity(entries, HttpStatus.OK);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{username}")
    public ResponseEntity createEntry(@PathVariable String username, @RequestBody JournalEntry journalEntry)
    {
        try {
            journalEntryService.saveEntry(journalEntry, username);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Created journal entry successfully");
        }
        catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getJournalEntryById(@PathVariable ObjectId id)
    {
         JournalEntry journalEntry = journalEntryService.getJournalEntryById(id);
         if(journalEntry != null)
            return new ResponseEntity(journalEntry, HttpStatus.OK);

         return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myId}/{username}")
    public ResponseEntity deleteJournalEntryByIdOfUser(@PathVariable ObjectId myId, @PathVariable String username)
    {
        journalEntryService.deleteEntry(myId, username);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Journal Entry Deleted Successfully");
    }

    @PutMapping("/{myId}/{username}")
    public ResponseEntity updateJournalEntryOfUser(
            @PathVariable ObjectId myId,
            @PathVariable String username,
            @RequestBody JournalEntry journalEntry)
    {
        journalEntryService.updateEntry(myId, username, journalEntry);
        return new ResponseEntity("Journal Entry Updated Successfully", HttpStatus.NO_CONTENT);
    }
}
