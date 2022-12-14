package com.kristina.dataapi.consent;

import com.kristina.dataapi.consent.repository.ConsentRepository;
import com.kristina.dataapi.data.DataService;
import com.kristina.dataapi.consent.model.Consent;
import com.kristina.dataapi.dialog.DialogService;
import com.kristina.dataapi.dialog.model.Dialog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ConsentServiceImpl implements ConsentService {

    private final DataService dataService;
    private final ConsentRepository consentRepository;
    private final DialogService dialogService;

    public ConsentServiceImpl(DataService dataService, ConsentRepository consentRepository, DialogService dialogService) {
        this.dataService = dataService;
        this.consentRepository = consentRepository;
        this.dialogService = dialogService;
    }

    @Override
    public void handle(Consent consent, Long dialogId) {
        Dialog dialog = saveConsent(consent, dialogId);
        handleDataOnRefusal(consent, dialog);
    }

    private Dialog saveConsent(Consent consent, Long dialogId) {
        Dialog dialog = dialogService.getDialogReference(dialogId);
        dialog.setConsent(consentRepository.save(consent));
        Dialog savedDialog = dialogService.saveDialog(dialog);
        return savedDialog;
    }

    private void handleDataOnRefusal(Consent consent, Dialog dialog) {
        if (!consent.isConsentGiven()) {
            dataService.deleteData(dialog.getId());
        }
    }
}
