package com.skilltrack.notification.repository;

import com.skilltrack.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    Page<Notification> findBySenderIdOrderBySentAtDesc(UUID userId, Pageable pageable);

    Page<Notification> findBySenderIdAndReadOrderBySentAtDesc(UUID userId, boolean read, Pageable pageable);

    @Query("{ 'sender_id' : ?0, 'read' : false }")
    @Update("{ '$set' : { 'read' : true, 'readAt' : ?1 } }")
    void markAllAsRead(UUID userId, LocalDateTime now);

    @Query("{ 'sender_id' : ?0, 'read' : false }")
    @Update("{ '$set' : { 'read' : true, 'readAt' : new Date() } }")
    void markAllAsRead(UUID userId);

    long countBySenderIdAndRead(UUID userId, boolean read);
}
