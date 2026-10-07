package com.projectproof.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectproof.entity.Notification;
import com.projectproof.entity.NotificationType;
import com.projectproof.entity.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    List<Notification> findByUserAndIsRead(User user, Boolean isRead);

    List<Notification> findByType(NotificationType type);

    long countByUserAndIsRead(User user, Boolean isRead);
}