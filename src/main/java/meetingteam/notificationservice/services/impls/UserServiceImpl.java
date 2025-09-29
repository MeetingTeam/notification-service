package meetingteam.notificationservice.services.impls;

import java.net.URI;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import meetingteam.commonlibrary.utils.AuthUtil;
import meetingteam.notificationservice.configs.ServiceUrlConfig;
import meetingteam.notificationservice.dtos.User.ResUserDto;
import meetingteam.notificationservice.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final RestClient restClient;
    private final ServiceUrlConfig serviceUrlConfig;

    public List<ResUserDto> getUsersByIds(List<String> userIds) {
        URI uri= UriComponentsBuilder.fromHttpUrl(serviceUrlConfig.userServiceUrl())
                .path("/user/private/by-ids")
                .build().toUri();

        return restClient.post()
                .uri(uri)
                .body(userIds)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }
}
