package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.services.JournalEntryService;
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


    @GetMapping()
    public ResponseEntity getAll()
    {
        List<JournalEntry> entries = journalEntryService.getAllEntries();
        return new ResponseEntity(entries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createEntry(@RequestBody JournalEntry journalEntry)
    {
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry entry = journalEntryService.saveEntry(journalEntry);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entry);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity getJournalEntryById(@PathVariable String myId)
    {
        JournalEntry journalEntry = journalEntryService.getEntry(myId);
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(journalEntry);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity deleteJournalEntryById(@PathVariable String myId)
    {
        JournalEntry journalEntry = journalEntryService.deleteEntry(myId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(journalEntry);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry journalEntry)
    {
        JournalEntry updatedEntry = journalEntryService.updateEntry(myId, journalEntry);
        return new ResponseEntity(updatedEntry, HttpStatus.OK);
    }
}
