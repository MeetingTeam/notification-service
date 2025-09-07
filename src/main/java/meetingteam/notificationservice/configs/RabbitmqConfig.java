package meetingteam.notificationservice.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class RabbitmqConfig {
    @Value("${rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${rabbitmq.notification-routing-key}")
    private String notificationRoutingKey;

    @Value("${rabbitmq.notification-queue-name}")
    private String notificationQueueName;

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueueName, true, false, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(notificationQueue)
                .to(exchange)
                .with(notificationRoutingKey);
    }
}
