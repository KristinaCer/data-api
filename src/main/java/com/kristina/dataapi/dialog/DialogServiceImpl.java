package com.kristina.dataapi.dialog;

import com.kristina.dataapi.customer.CustomerService;
import com.kristina.dataapi.customer.model.Customer;
import com.kristina.dataapi.dialog.model.Dialog;
import com.kristina.dataapi.dialog.repository.DialogRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DialogServiceImpl implements DialogService {
    private final DialogRepository dialogRepository;
    private final CustomerService customerService;

    public DialogServiceImpl(DialogRepository dialogRepository, CustomerService customerService) {
        this.dialogRepository = dialogRepository;
        this.customerService = customerService;
    }

    @Override
    public Optional<Dialog> getDialog(Long dialogId) {
        return dialogRepository.findById(dialogId);
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
