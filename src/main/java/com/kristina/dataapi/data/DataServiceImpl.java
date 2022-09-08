package com.kristina.dataapi.data;

import com.kristina.dataapi.data.dto.CustomerMessageDTO;
import com.kristina.dataapi.data.model.*;
import com.kristina.dataapi.dialog.DialogService;
import com.kristina.dataapi.data.repository.MessageRepository;
import com.kristina.dataapi.dialog.model.Dialog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {
    private final MessageRepository messageRepository;
    private final DialogService dialogService;

    public DataServiceImpl(MessageRepository messageRepository, DialogService dialogService) {
        this.messageRepository = messageRepository;
        this.dialogService = dialogService;
    }

    @Override
    public Message saveMessage(Long customerId, Long dialogId, Message message) {
        Dialog dialog = dialogService.getDialog(dialogId, customerId);
        message.setDialog(dialog);
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public void deleteData(Long dialogId) {
        messageRepository.deleteMessages(dialogId);
    }


    @Override
    public CustomerMessageDTO retrieveData(Optional<Long> customerId, Optional<Language> language) {
        return null;
    }
}
