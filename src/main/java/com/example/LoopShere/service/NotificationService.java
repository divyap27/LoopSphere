package com.example.LoopShere.service;

import com.example.LoopShere.model.Notification;

import java.util.List;
public interface NotificationService {
Notification create(Notification notification);
    List<Notification> getAllByUser(Long userId);
    void markAllAsRead(Long userId);
    void delete(Long id);

}
