package meetingteam.notificationservice.dtos.Notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MailDto {
    private String subject;

    private String content;
    
    private List<String> recipientIds;
}
