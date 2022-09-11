package com.kristina.dataapi.data;

import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/{customerId}/{dialogId}")
    public ResponseEntity<Message> save(@PathVariable Long customerId,
                                        @PathVariable Long dialogId,
                                        @RequestBody Message message){
        Message savedMessage = dataService.saveMessage(dialogId, message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Message>> retrieveData(@RequestParam(required = false) Language language,
                                                      @RequestParam(required = false) Long customerId,
                                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(defaultValue = "createDateTime") String sortBy) {
        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Message> data = dataService.retrieveData(language, customerId, paging);
        return ResponseEntity.ok(data);
    }
}
