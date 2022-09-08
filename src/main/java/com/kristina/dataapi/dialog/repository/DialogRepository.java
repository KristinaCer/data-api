package com.kristina.dataapi.dialog.repository;

import com.kristina.dataapi.dialog.model.Dialog;
import com.kristina.dataapi.data.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {


    /**
     * that match the query params (if any)
     * for which we have consent for
     * and sorted by most recent data first
     */

    @Query()
    Optional<List<Dialog>> getDialogs(Long customerId, Language language);
}
