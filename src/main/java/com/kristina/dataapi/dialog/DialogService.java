package com.kristina.dataapi.dialog;

import com.kristina.dataapi.dialog.model.Dialog;

import java.util.Optional;

public interface DialogService {
    Dialog getDialogReference(Long dialogId);
    Optional<Dialog> getDialog(Long dialogId);
    Dialog saveDialog(Dialog dialog);
    Dialog createDialog(Long customerId);
}
