package com.kristina.dataapi.data.repository;

import com.kristina.dataapi.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    /*
    I could not use join with delete as it seems to be not working with
    H2 db:
    https://github.com/vitalidze/traccar-web/issues/707
     */
    @Modifying
    @Query(value = "delete from Message as m " +
            "where m.dialog.id = :dialogId")
    void deleteMessages(Long dialogId);
}
