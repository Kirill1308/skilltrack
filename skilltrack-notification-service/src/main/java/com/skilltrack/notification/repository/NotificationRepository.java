package com.skilltrack.notification.repository;

import com.skilltrack.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    Page<Notification> findBySenderIdOrderBySentAtDesc(UUID userId, Pageable pageable);

}
