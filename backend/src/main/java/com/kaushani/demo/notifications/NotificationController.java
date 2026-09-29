package com.kaushani.demo.notifications;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaushani.demo.notifications.dto.SendNoticeRequest;
import com.kaushani.demo.notifications.dto.SendReminderRequest;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    

    @PreAuthorize("hasRole('TENANT')")
    @GetMapping("/me")
    public List<Notification> getMyNotifications(Authentication authentication) {
        return notificationService.getMyNotifications(authentication.getName());
    }

    

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/notice")
    public Notification sendNoticeToAll(@RequestBody SendNoticeRequest request) {
        return notificationService.sendNoticeToAll(request.getTitle(), request.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/reminder")
    public Notification sendReminder(@RequestBody SendReminderRequest request) {
        return notificationService.sendReminderToTenant(
                request.getTenantId(), request.getTitle(), request.getMessage());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/notices")
    public List<Notification> getNotices() {
        return notificationService.getNotices();
    }
}