package com.kristina.dataapi.data;

import com.kristina.dataapi.data.model.*;
import com.kristina.dataapi.data.repository.DialogRepository;
import com.kristina.dataapi.data.repository.MessageRepository;
import com.kristina.dataapi.exception.DialogNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {
    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;

    public DataServiceImpl(DialogRepository dialogRepository, MessageRepository messageRepository) {
        this.dialogRepository = dialogRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Long customerId, Long dialogId, Message message) {
        Dialog dialog = getDialog(dialogId, customerId);
        message.setDialog(dialog);
        return messageRepository.save(message);
    }

    @Override
    public Dialog getDialog(Long dialogId, Long customerId) {
        return dialogRepository.findById(dialogId).orElseThrow(() -> new DialogNotFoundException());
    }

    @Override
    public Dialog saveDialog(Dialog dialog) {
        return dialogRepository.save(dialog);
    }

    @Override
    @Transactional
    public void deleteData(Long dialogId) {
        messageRepository.deleteMessages(dialogId);
    }

    @Override
    public Dialog getDialogReference(Long dialogId) {
        return dialogRepository
                .getReferenceById(dialogId);
    }

    @Override
    public CustomerMessageDTO retrieveData(Optional<Long> customerId, Optional<Language> language) {
        return null;
    }
}
