package com.kristina.dataapi.data;

import com.kristina.dataapi.data.model.Dialog;
import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/{customerId}/{dialogId}")
    public ResponseEntity<Message> save(@PathVariable Long customerId, @PathVariable Long dialogId, @RequestBody Message message) {
        Message savedMessage = dataService.saveMessage(customerId, dialogId, message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/")
    public ResponseEntity<List<Dialog>> retrieveData(@RequestParam Optional<Long> customerId, @RequestParam Optional<Language> language) {
        List<Dialog> data = dataService.retrieveData(customerId, language);
        return ResponseEntity.ok(data);
    }
}
