package com.org.cards.cards.repositories;

import com.org.cards.cards.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByMobileNumber(String mobileNumber);

    void deleteByMobileNumber(String mobileNumber);
}
