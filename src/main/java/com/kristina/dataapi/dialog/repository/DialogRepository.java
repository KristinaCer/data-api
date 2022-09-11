package com.kristina.dataapi.dialog.repository;

import com.kristina.dataapi.dialog.model.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {

}
