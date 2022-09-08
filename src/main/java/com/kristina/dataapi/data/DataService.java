package com.kristina.dataapi.data;

import com.kristina.dataapi.data.model.Dialog;
import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;

import java.util.List;
import java.util.Optional;

public interface DataService {
    Dialog getDialogReference(Long dialogId);

    Dialog getDialog(Long dialogId, Long customerId);

    List<Dialog> retrieveData(Optional<Long> customerId, Optional<Language> language);

    Message saveMessage(Long customerId, Long dialogId, Message messageDTO);

    Dialog saveDialog(Dialog dialog);

    void deleteData(Long id);
}
