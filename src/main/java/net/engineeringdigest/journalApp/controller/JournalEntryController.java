package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<String, JournalEntry> journalEntryMap =  new HashMap<>();

    @GetMapping()
    public List<JournalEntry> getAll()
    {
        return new ArrayList<>(journalEntryMap.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry)
    {
        journalEntryMap.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable long myId)
    {
        return journalEntryMap.get(myId);
    }

    @DeleteMapping("/id/{myId}")
    public JournalEntry deleteJournalEntryById(@PathVariable long myId)
    {
        return journalEntryMap.remove(myId);
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry journalEntry)
    {
        return journalEntryMap.put(myId, journalEntry);
    }
}
