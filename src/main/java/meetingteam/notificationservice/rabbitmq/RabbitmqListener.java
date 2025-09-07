package meetingteam.notificationservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meetingteam.notificationservice.dtos.Notification.MailDto;
import meetingteam.notificationservice.services.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitmqListener {
    private final ObjectMapper objectMapper=new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final MailService mailService;

    @RabbitListener(queues = "#{notificationQueue.name}")
    public void listenMailDtoMessage(String mailDtoStr){
        try{
            MailDto mailDto = objectMapper.readValue(mailDtoStr, MailDto.class);
            mailService.sendMailToMultipleDests(mailDto);
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
    }
}
