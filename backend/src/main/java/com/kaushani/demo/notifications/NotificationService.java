package com.kaushani.demo.notifications;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaushani.demo.tenant.Tenant;
import com.kaushani.demo.tenant.TenantService;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final TenantService tenantService;

    public NotificationService(NotificationRepository notificationRepository,
                               EmailService emailService,
                               TenantService tenantService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
        this.tenantService = tenantService;
    }

    @Transactional
    public Notification sendNoticeToAll(String title, String message) {

        List<Tenant> activeTenants = tenantService.getActiveTenants();

        for (Tenant tenant : activeTenants) {
            emailService.sendEmail(tenant.getUser().getEmail(), title, message);
        }

        Notification notification = new Notification(title, message, NotificationType.NOTICE, null);
        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification sendReminderToTenant(Long tenantId, String title, String message) {

        Tenant tenant = tenantService.getTenantById(tenantId);

        emailService.sendEmail(tenant.getUser().getEmail(), title, message);

        Notification notification = new Notification(title, message, NotificationType.PAYMENT_REMINDER, tenant);
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotices() {
        return notificationRepository.findByTenantIdIsNull();
    }

    public List<Notification> getMyNotifications(String email) {
        Tenant tenant = tenantService.getMyProfile(email);

        List<Notification> personal = notificationRepository.findByTenantId(tenant.getId());
        List<Notification> broadcasts = notificationRepository.findByTenantIdIsNull();

        personal.addAll(broadcasts);
        return personal;
    }
}