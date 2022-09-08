package com.kristina.dataapi.data;

import com.kristina.dataapi.dialog.model.Dialog;
import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;

import java.util.List;
import java.util.Optional;

public interface DataService {

    List<Dialog> retrieveData(Optional<Long> customerId, Optional<Language> language);

    Message saveMessage(Long customerId, Long dialogId, Message messageDTO);

    void deleteData(Long id);
}
