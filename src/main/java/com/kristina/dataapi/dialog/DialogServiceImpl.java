package com.kristina.dataapi.dialog;

import com.kristina.dataapi.customer.CustomerService;
import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.dialog.model.Dialog;
import com.kristina.dataapi.dialog.repository.DialogRepository;
import com.kristina.dataapi.exception.DialogNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DialogServiceImpl implements DialogService{
    private final DialogRepository dialogRepository;
    private final CustomerService customerService;

    public DialogServiceImpl(DialogRepository dialogRepository, CustomerService customerService) {
        this.dialogRepository = dialogRepository;
        this.customerService = customerService;
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
    public Dialog createDialog(Long customerId) {
        Customer customer = customerService.get(customerId);
        return dialogRepository.save(new Dialog(customer));
    }

    @Override
    public Dialog getDialogReference(Long dialogId) {
        return dialogRepository
                .getReferenceById(dialogId);
    }
}
