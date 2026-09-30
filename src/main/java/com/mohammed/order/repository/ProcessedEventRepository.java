package com.mohammed.order.repository;

import com.mohammed.order.entity.ProcessedEventEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class ProcessedEventRepository implements PanacheRepositoryBase<ProcessedEventEntity, UUID> {
    public boolean isProcessed(UUID eventId) {
        return findByIdOptional(eventId).isPresent();
    }

    public void markAsProcessed(UUID eventId) {

        ProcessedEventEntity entity = new ProcessedEventEntity();

        entity.eventId = eventId;
        entity.processedAt = LocalDateTime.now();

        persist(entity);
    }
}
