package com.kristina.dataapi.consent;

import com.kristina.dataapi.consent.model.Consent;

public interface ConsentService {
    void handle(Consent consent, Long dialogId);
}
