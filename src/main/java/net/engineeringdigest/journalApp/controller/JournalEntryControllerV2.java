package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.services.JournalEntryService;
import net.engineeringdigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal/v2")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;


    @GetMapping("/get-all-entries")
    public ResponseEntity getAllJournalEntriesOfUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<JournalEntry> entries = journalEntryService.getAllJournalEntriesOfUser(username);
        if(entries != null && !entries.isEmpty())
            return new ResponseEntity(entries, HttpStatus.OK);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-entry")
    public ResponseEntity createEntry(@RequestBody JournalEntry journalEntry)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(journalEntry, username);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Created journal entry successfully");
        }
        catch(Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity getJournalEntryById(@PathVariable ObjectId id)
    {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String username = authentication.getName();
         User user = userService.findByUsername(username)
                 .orElseThrow(() -> new UserNotFoundException("Username not found"));
         JournalEntry journalEntry = journalEntryService.getJournalEntryById(user, id);
         if(journalEntry != null)
            return new ResponseEntity(journalEntry, HttpStatus.OK);

         return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myId}")
    public ResponseEntity deleteJournalEntryByIdOfUser(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.deleteEntry(myId, username);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Journal Entry Deleted Successfully");
    }

    @PutMapping("/{myId}")
    public ResponseEntity updateJournalEntryOfUser(@PathVariable ObjectId myId, @RequestBody JournalEntry journalEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.updateEntry(myId, username, journalEntry);
        return new ResponseEntity("Journal Entry Updated Successfully", HttpStatus.NO_CONTENT);
    }
}
