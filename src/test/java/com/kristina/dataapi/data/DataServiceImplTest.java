package com.kristina.dataapi.data;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    DialogRepository dialogRepository;
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
        Dialog dialog = getOneDialogData();
        dialog.setId(1L);
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
    void retrieveData() {

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

    private Dialog getOneDialogData() {

        Dialog dialog = new Dialog();
        Customer customer = new Customer();
        dialog.setCustomer(customer);

        Message message1 = new Message();
        message1.setText("Testing user data input 1.");
        message1.setLanguage(Language.CH);
        message1.setCreateDateTime(LocalDateTime.now());
        message1.setDialog(dialog);

        Message message2 = new Message();
        message2.setText("Testing user data input 2.");
        message2.setLanguage(Language.CH);
        message2.setCreateDateTime(LocalDateTime.now());
        message2.setDialog(dialog);

        return dialog;
    }


}