package com.kristina.dataapi.data;

import com.kristina.dataapi.consent.model.Consent;
import com.kristina.dataapi.consent.repository.ConsentRepository;
import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import com.kristina.dataapi.data.repository.MessageRepository;
import com.kristina.dataapi.dialog.DialogServiceImpl;
import com.kristina.dataapi.dialog.model.Dialog;
import com.kristina.dataapi.dialog.repository.DialogRepository;
import com.kristina.dataapi.exception.DialogNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ContextConfiguration(classes = {DataServiceImpl.class})
@ExtendWith(SpringExtension.class)
class DataServiceImplTest {

    @MockBean
    private MessageRepository messageRepository;
    @MockBean
    private DialogServiceImpl dialogService;
    @MockBean
    private DialogRepository dialogRepository;
    @MockBean
    private ConsentRepository consentRepository;
    @Autowired
    private DataServiceImpl dataService;

    @Test
    void saveMessage_success() {
        //given
        Message message = createMessage();
        message.setId(1L);
        Dialog dialog = createDialog();
        dialog.setId(1L);
        given(dialogService.getDialog(dialog.getId())).willReturn(Optional.of(dialog));
        //when
        dataService.saveMessage(dialog.getId(), message);
        //then
        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(messageArgumentCaptor.capture());
        Message capturedMessage = messageArgumentCaptor.getValue();
        assertThat(capturedMessage).isEqualTo(message);
    }

    @Test
    void saveMessage_whenDialogNotExists_throwsDialogNotFoundException() {
        //given
        Message message = createMessage();
        message.setId(1L);
        Long dialogId = 234L;
        given(dialogService.getDialog(dialogId)).willReturn(Optional.ofNullable(null));
        // when
        assertThrows(DialogNotFoundException.class, () -> dataService.saveMessage(dialogId, message));
    }

    @Test
    void deleteData_success() {
        //given
        Message message1 = createMessage();
        Message message2 = createMessage();
        Dialog dialog = createDialog();
        dialog.setId(1L);
        message1.setDialog(dialog);
        message2.setDialog(dialog);
        given(dialogService.getDialog(dialog.getId())).willReturn(Optional.of(dialog));
        // when
        dataService.deleteData(dialog.getId());
        verify(messageRepository).deleteMessages(dialog.getId());
    }

    @Test
    void deleteData_whenDataNotExists_throwsDialogNotFoundException() {
        //given
        Long dialogId = 234L;
        given(dialogService.getDialog(dialogId)).willReturn(Optional.ofNullable(null));
        // when
        assertThrows(DialogNotFoundException.class, () -> dataService.deleteData(dialogId));
    }

    @Test
    void retrieveAllData_withConsent_success() {
        //given
        Dialog dialog = createDialog();
        dialog.setId(1L);
        dialog.getCustomer().setId(234L);
        Consent consent = getConsentNotGiven();
        consent.setId(1L);
        dialog.setConsent(consent);
        Message message1 = createMessage();
        message1.setDialog(dialog);
        Message message2 = createMessage();
        message2.setDialog(dialog);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        Page<Message> page = new PageImpl<>(messages);
        given(messageRepository.findAllWithConsent(PageRequest.of(0, 10, Sort.by("createDateTime")))).willReturn(page);
        //when
        Page<Message> allData = dataService.retrieveData(null, null, PageRequest.of(0, 10, Sort.by("createDateTime")));
        //then
        assertEquals(allData, page);
    }

    @Test
    void retrieveAllData_whenConsentNotGiven_returnsEmptyPage_success() {
        //given
        Dialog dialog = createDialog();
        dialog.setId(1L);
        dialog.getCustomer().setId(234L);
        Consent consent = getConsentNotGiven();
        consent.setId(1L);
        dialog.setConsent(consent);

        Page<Message> page = new PageImpl<>(Collections.emptyList());

        given(messageRepository.findAllWithConsent(PageRequest.of(0, 10, Sort.by("createDateTime")))).willReturn(page);
        //when
        Page<Message> allData = dataService.retrieveData(null, null, PageRequest.of(0, 10, Sort.by("createDateTime")));
        //then
        assertEquals(allData, page);
    }

    @Test
    void retrieveAllData_withConsent_forSpecifiedCustomer_success() {
        Dialog dialog = createDialog();
        dialog.setId(1L);
        dialog.getCustomer().setId(234L);
        Consent consent = getConsentGiven();
        consent.setId(1L);
        dialog.setConsent(consent);

        Message message1 = createMessage();
        message1.setDialog(dialog);
        Message message2 = createMessage();
        message2.setDialog(dialog);

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        Page<Message> page = new PageImpl<>(messages);

        given(messageRepository.findByCustomerId(234L, PageRequest.of(0, 10, Sort.by("createDateTime")))).willReturn(page);
        //when
        Page<Message> allData = dataService.retrieveData(null, 234L, PageRequest.of(0, 10, Sort.by("createDateTime")));
        //then
        assertEquals(allData, page);
    }

    @Test
    void retrieveAllData_withConsent_forSpecifiedLanguage_success() {
        Dialog dialog = createDialog();
        dialog.setId(1L);
        dialog.getCustomer().setId(10L);
        Consent consent = getConsentGiven();
        consent.setId(1L);
        dialog.setConsent(consent);

        Message message1 = createMessage();
        message1.setLanguage(Language.DE);
        message1.setDialog(dialog);
        Message message2 = createMessage();
        message2.setLanguage(Language.DE);
        message2.setDialog(dialog);

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        Page<Message> page = new PageImpl<>(messages);

        given(messageRepository.findByLanguage(Language.DE, PageRequest.of(0, 10, Sort.by("createDateTime")))).willReturn(page);
        //when
        Page<Message> allData = dataService.retrieveData(Language.DE, null, PageRequest.of(0, 10, Sort.by("createDateTime")));
        //then
        assertEquals(allData, page);
    }

    @Test
    void retrieveAllData_withConsent_forSpecifiedCustomerAndLanguage_success() {
        Message message1 = createMessage();
        Message message2 = createMessage();
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        Page<Message> page = new PageImpl<>(messages);

        given(messageRepository.findByCustomerIdAndLanguage(234L, Language.CH, PageRequest.of(0, 10, Sort.by("createDateTime")))).willReturn(page);
        //when
        Page<Message> allData = dataService.retrieveData(Language.CH, 234L, PageRequest.of(0, 10, Sort.by("createDateTime")));
        //then
        assertEquals(allData, page);
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
        Customer customer = new Customer();
        dialog.setCustomer(customer);
        return dialog;
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

}