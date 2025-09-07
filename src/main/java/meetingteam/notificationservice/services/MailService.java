package meetingteam.notificationservice.services;

import meetingteam.notificationservice.dtos.Notification.MailDto;

public interface MailService {
    void sendMailToMultipleDests(MailDto dto);
    void sendFakeEmail(String to, String subject, String content);
    void sendEmail(String to, String subject, String content);
}
