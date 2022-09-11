package com.kristina.dataapi.data.repository;

import com.kristina.dataapi.data.model.Language;
import com.kristina.dataapi.data.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Modifying
    @Query(value = "delete " +
            "from Message as m " +
            "where m.dialog.id = :dialogId")
    void deleteMessages(Long dialogId);

    @Query(value = "select m " +
            "from Message as m " +
            "where m.dialog.consent.consentGiven = true")
    Page<Message> findAllWithConsent(Pageable paging);

    Page<Message> findByLanguage(Language language, Pageable paging);

    @Query(value = "select m " +
            "from Message as m " +
            "where m.dialog.customer.id = :customerId and m.dialog.consent.consentGiven = true")
    Page<Message> findByCustomerId(Long customerId, Pageable paging);

    @Query(value = "select m " +
            "from Message as m " +
            "where m.dialog.customer.id = :customerId and m.language = :language and m.dialog.consent.consentGiven = true")
    Page<Message> findByCustomerIdAndLanguage(Long customerId, Language language, Pageable paging);
}
