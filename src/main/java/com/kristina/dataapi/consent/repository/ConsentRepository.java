package com.kristina.dataapi.consent.repository;

import com.kristina.dataapi.consent.model.Consent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<Consent, Long> {
}
