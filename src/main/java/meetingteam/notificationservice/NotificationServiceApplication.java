package meetingteam.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import meetingteam.notificationservice.configs.AnomalyConfig;
import meetingteam.notificationservice.configs.ServiceUrlConfig;

@SpringBootApplication
@EnableConfigurationProperties({ServiceUrlConfig.class, AnomalyConfig.class})
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

}
