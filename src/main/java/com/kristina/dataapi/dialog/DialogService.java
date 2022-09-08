package com.kristina.dataapi.dialog;

import com.kristina.dataapi.dialog.model.Dialog;

public interface DialogService {
    Dialog getDialogReference(Long dialogId);

    Dialog getDialog(Long dialogId, Long customerId);
    Dialog saveDialog(Dialog dialog);
    Dialog createDialog(Long customerId);
}
