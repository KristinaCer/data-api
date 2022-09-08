package com.kristina.dataapi.consent;

import com.kristina.dataapi.consent.model.Consent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consents")
public class ConsentController {
    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping("/{dialogId}")
    public ResponseEntity<?> handleConsent(@RequestBody Consent consent, @PathVariable Long dialogId) {
        consentService.handle(consent, dialogId);
        return ResponseEntity.ok().build();
    }
}
