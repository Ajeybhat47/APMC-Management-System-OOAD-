package com.ooad.apmc.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import com.ooad.apmc.Models.Notification;
import com.ooad.apmc.Service.NotificationService;

import org.springframework.ui.Model;

import java.util.List;


// import

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{userId}/getNotifications")
    public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @GetMapping("/user/{userId}")
    public String getNotificationsByUserId(@PathVariable Long userId, Model model) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        model.addAttribute("notifications", notifications);
        return "notification";
    }

    @GetMapping("/getAllNotifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping("/createNotification")
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
        return ResponseEntity.ok("Notification created successfully");
    }

    @DeleteMapping("/{id}/deleteNotification")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok("Notification deleted successfully");
    }

    @GetMapping("/getNotification/{id}/")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @GetMapping("/{id}")
public String getNotificationById(@PathVariable Long id, Model model) {
    Notification notification = notificationService.getNotificationById(id);
    model.addAttribute("notification", notification);
    return "notification";
}

    @DeleteMapping("/{userId}/deleteAllNotifications")
    public ResponseEntity<String> deleteAllNotificationsForUser(@PathVariable Long userId) {
        notificationService.deleteAllNotificationsForUser(userId);
        return ResponseEntity.ok("All notifications deleted successfully");
    }

    @DeleteMapping("/deleteAllNotifications")
    public ResponseEntity<String> deleteAllNotifications() {
        notificationService.deleteAllNotifications();
        return ResponseEntity.ok("All notifications deleted successfully");
    }



}


// @Controller
// @RequestMapping("/notifications")
// public class NotificationController {

//     @Autowired
//     private NotificationService notificationService;

//     @GetMapping("/{userId}/getNotifications")
//     public ResponseEntity<List<Notification>> getNotificationsForUser(@PathVariable Long userId) {
//         return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
//     }

//     @GetMapping("/user/{userId}")
//     public String getNotificationsByUserId(@PathVariable Long userId, Model model) {
//         List<Notification> notifications = notificationService.getNotificationsForUser(userId);
//         model.addAttribute("notifications", notifications);
//         return "notification";
//     }

//     @GetMapping("/getAllNotifications")
//     public ResponseEntity<List<Notification>> getAllNotifications() {
//         return ResponseEntity.ok(notificationService.getAllNotifications());
//     }

//     @PostMapping("/createNotification")
//     public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
//         notificationService.createNotification(notification);
//         return ResponseEntity.ok("Notification created successfully");
//     }

//     @DeleteMapping("/{id}/deleteNotification")
//     public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
//         notificationService.deleteNotification(id);
//         return ResponseEntity.ok("Notification deleted successfully");
//     }

//     @GetMapping("/getNotification/{id}/")
//     public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
//         return ResponseEntity.ok(notificationService.getNotificationById(id));
//     }

//     @GetMapping("/{id}")
// public String getNotificationById(@PathVariable Long id, Model model) {
//     Notification notification = notificationService.getNotificationById(id);
//     model.addAttribute("notification", notification);
//     return "notification";
// }

//     @DeleteMapping("/{userId}/deleteAllNotifications")
//     public ResponseEntity<String> deleteAllNotificationsForUser(@PathVariable Long userId) {
//         notificationService.deleteAllNotificationsForUser(userId);
//         return ResponseEntity.ok("All notifications deleted successfully");
//     }

//     @DeleteMapping("/deleteAllNotifications")
//     public ResponseEntity<String> deleteAllNotifications() {
//         notificationService.deleteAllNotifications();
//         return ResponseEntity.ok("All notifications deleted successfully");
//     }



// }
