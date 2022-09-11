package com.kristina.dataapi.data;

import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DataService {

    Page<Message> retrieveData(Language language, Long customerId, Pageable paging);

    Message saveMessage(Long dialogId, Message messageDTO);

    void deleteData(Long id);
}
