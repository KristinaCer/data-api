package com.kristina.dataapi.dialog;

import com.kristina.dataapi.dialog.model.Dialog;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dialogs")
public class DialogController {

    private final DialogService dialogService;

    public DialogController(DialogService dialogService) {
        this.dialogService = dialogService;
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<Dialog> save(@PathVariable Long customerId) {
        return ResponseEntity.ok(dialogService.createDialog(customerId));
    }
}
