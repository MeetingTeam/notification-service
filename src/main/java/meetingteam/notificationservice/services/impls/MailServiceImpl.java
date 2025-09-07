package meetingteam.notificationservice.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetingteam.notificationservice.dtos.Notification.MailDto;
import meetingteam.notificationservice.dtos.User.ResUserDto;
import meetingteam.notificationservice.services.MailService;
import meetingteam.notificationservice.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSenderImpl mailSender;
    private final UserService userService;

    @Value("${spring.mail.custom.sender-name}")
    private String senderName;

    @Override
    public void sendMailToMultipleDests(MailDto mailDto){
        List<ResUserDto> userDtos = userService.getUsersByIds(mailDto.getRecipientIds());
        
        for(var userDto: userDtos){
            sendFakeEmail(userDto.getEmail(), mailDto.getSubject(), mailDto.getContent());
        }
    }

    public void sendFakeEmail(String to, String subject, String content) {
        try {
            // delay 1 second
            Thread.sleep(1000);

            log.info("Send mail to {} successfully", to);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted flag
           log.error("Unable to send email to {}", to);
        }
    }
    
    public void sendEmail(String to, String subject, String content) {
        try{
            var message = mailSender.createMimeMessage();
            System.out.println("Message "+message);
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        }
        catch(Exception e){
            log.error("Unable to send email to {}",to);
        }
    }
}
