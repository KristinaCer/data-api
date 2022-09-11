package com.kristina.dataapi.data;

import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import com.kristina.dataapi.data.repository.MessageRepository;
import com.kristina.dataapi.dialog.DialogService;
import com.kristina.dataapi.dialog.model.Dialog;
import com.kristina.dataapi.exception.DialogNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataServiceImpl implements DataService {
    private final MessageRepository messageRepository;
    private final DialogService dialogService;

    public DataServiceImpl(MessageRepository messageRepository, DialogService dialogService) {
        this.messageRepository = messageRepository;
        this.dialogService = dialogService;
    }

    @Override
    public Message saveMessage(Long dialogId, Message message) {
        Dialog dialog = dialogService.getDialog(dialogId).orElseThrow(DialogNotFoundException::new);
        message.setDialog(dialog);
        return messageRepository.save(message);
    }

    @Override
    @Transactional
    public void deleteData(Long dialogId) {
        Dialog dialog = dialogService.getDialog(dialogId).orElseThrow(DialogNotFoundException::new);
        messageRepository.deleteMessages(dialog.getId());
    }

    @Override
    public Page<Message> retrieveData(Language language, Long customerId, Pageable paging) {
        if (language == null && customerId == null) {
            return messageRepository.findAllWithConsent(paging);
        } else if (customerId == null) {
            return messageRepository.findByLanguage(language, paging);
        } else if (language == null) {
            return messageRepository.findByCustomerId(customerId, paging);
        } else {
            return messageRepository.findByCustomerIdAndLanguage(customerId, language, paging);
        }
    }
}

