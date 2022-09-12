package com.kristina.dataapi.consent;

import com.kristina.dataapi.consent.model.Consent;
import com.kristina.dataapi.consent.repository.ConsentRepository;
import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.data.DataServiceImpl;
import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import com.kristina.dataapi.dialog.DialogServiceImpl;
import com.kristina.dataapi.dialog.model.Dialog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {ConsentServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConsentServiceImplTest {

    @MockBean
    private DialogServiceImpl dialogService;
    @MockBean
    private DataServiceImpl dataService;
    @MockBean
    private ConsentRepository consentRepository;
    @Autowired
    private ConsentServiceImpl consentService;

    @Test
    void handle_consentGiven_savesConsent() {
        //given
        Dialog dialog = createDialog();
        dialog.setId(1L);
        Message message1 = createMessage();
        message1.setId(1L);
        message1.setDialog(dialog);
        Consent consent = getConsentGiven();
        consent.setId(1L);
        dialog.setConsent(consent);

        given(dialogService.getDialogReference(dialog.getId())).willReturn(dialog);
        //when
        consentService.handle(consent, dialog.getId());
        //then
        ArgumentCaptor<Consent> consentArgumentCaptor = ArgumentCaptor.forClass(Consent.class);
        verify(consentRepository).save(consentArgumentCaptor.capture());
        Consent capturedConsent = consentArgumentCaptor.getValue();
        assertThat(capturedConsent).isEqualTo(consent);

    }

    @Test
    void handle_consentNotGiven_savesConsentAndDeletesDialogData() {
        //given
        Dialog dialog = createDialog();
        dialog.setId(1L);
        Message message1 = createMessage();
        message1.setId(1L);
        message1.setDialog(dialog);
        Consent consent = getConsentNotGiven();
        consent.setId(1L);
        dialog.setConsent(consent);

        given(dialogService.getDialogReference(dialog.getId())).willReturn(dialog);
        given(dialogService.saveDialog(dialog)).willReturn(dialog);
        //when
        consentService.handle(consent, dialog.getId());
        //then
        ArgumentCaptor<Consent> consentArgumentCaptor = ArgumentCaptor.forClass(Consent.class);
        verify(consentRepository).save(consentArgumentCaptor.capture());
        Consent capturedConsent = consentArgumentCaptor.getValue();
        assertThat(capturedConsent).isEqualTo(consent);

        verify(dataService).deleteData(dialog.getId());
    }

    private Consent getConsentGiven() {
        Consent consent = new Consent();
        consent.setConsentGiven(true);
        return consent;
    }

    private Consent getConsentNotGiven() {
        Consent consent = new Consent();
        consent.setConsentGiven(false);
        return consent;
    }

    private Message createMessage() {
        Message message = new Message();
        message.setText("Testing user data input.");
        message.setLanguage(Language.CH);
        message.setCreateDateTime(LocalDateTime.now());
        return message;
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        Customer customer = new Customer(1L);
        dialog.setCustomer(customer);
        return dialog;
    }
}