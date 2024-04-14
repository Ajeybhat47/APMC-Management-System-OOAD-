package com.ooad.apmc.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ooad.apmc.Models.*;
import com.ooad.apmc.Repository.*;

import java.util.List;

@Service
public class NotificationService{

    @Autowired

    private NotificationRepository notificationRepository;

    @Autowired
private WorkerRepository workerRepository;

public void notifyAllWorkers(String message) {
    // Get a list of all workers
    List<Worker> workers = workerRepository.findAll();

    // Create and save a new notification for each worker
    for (Worker worker : workers) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(worker);
        notification.setStatus("Pending");

        notificationRepository.save(notification);
    }
}
    public List<Notification> getNotificationsForUser(Long userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserId(userId);

        // Set the status of each notification to read
        for (Notification notification : notifications) {
            notification.setStatus("read");
            notificationRepository.save(notification);
        }

        return notifications;    }
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public void deleteAllNotificationsForUser(Long userId) {
        List<Notification> notifications = notificationRepository.findByUser_UserId(userId);
        for (Notification notification : notifications) {
            notificationRepository.deleteById(notification.getNotificationId());
        }
    }

    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }


}