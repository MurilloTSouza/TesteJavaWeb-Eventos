package com.behoh.events.repository;

import com.behoh.events.model.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository
    extends JpaRepository<EventoEntity, Integer> {
}
