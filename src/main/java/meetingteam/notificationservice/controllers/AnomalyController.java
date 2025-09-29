package meetingteam.notificationservice.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import meetingteam.commonlibrary.dtos.PagedResponseDto;
import meetingteam.commonlibrary.exceptions.InternalServerException;
import meetingteam.commonlibrary.utils.AuthUtil;
import meetingteam.notificationservice.configs.ServiceUrlConfig;

@RestController
@RequestMapping("/anomaly")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AnomalyController {
    private final RestClient restClient;
    private final ServiceUrlConfig serviceUrlConfig;

    @PostMapping("/calendar/{meetingId}")
    public ResponseEntity<Void> addToCalendar(
            @PathVariable("meetingId") String meetingId,
            @RequestParam("isAdded") Boolean isAdded){
        String jwtToken = AuthUtil.getJwtToken();

        var uriBuilder= UriComponentsBuilder
            .fromHttpUrl(serviceUrlConfig.meetingServiceUrl())
            .path("/calendar/"+meetingId)
            .queryParam("isAdded", isAdded);
        URI uri = uriBuilder.build().toUri();

        restClient.post()
                .uri(uri)
                .headers(h->h.setBearerAuth(jwtToken))
                .retrieve()
                .toBodilessEntity();
         return ResponseEntity.ok().build();
    }

    @GetMapping("/message/friend/{friendId}")
    public ResponseEntity<Void> getFriendMessages(
            @RequestParam Integer receivedMessageNum,
            @PathVariable String friendId){
        String jwtToken = AuthUtil.getJwtToken();

        var uriBuilder= UriComponentsBuilder
            .fromHttpUrl(serviceUrlConfig.chatServiceUrl())
            .path("/message/friend/"+friendId)
            .queryParam("receivedMessageNum", receivedMessageNum);
        URI uri = uriBuilder.build().toUri();

        restClient.get()
            .uri(uri)
            .headers(h->h.setBearerAuth(jwtToken))
            .retrieve()
            .toBodilessEntity();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/private/email")
    public ResponseEntity<Void> getEmail(){
        String jwtToken = AuthUtil.getJwtToken();

        var uriBuilder= UriComponentsBuilder
            .fromHttpUrl(serviceUrlConfig.userServiceUrl())
            .path("/user/private/email");
        URI uri = uriBuilder.build().toUri();

        restClient.get()
                .uri(uri)
                .headers(h->h.setBearerAuth(jwtToken))
                .retrieve()
                .toBodilessEntity();
         return ResponseEntity.ok().build();
    }

    @GetMapping("/team")
    public ResponseEntity<Void> getJoinedTeams(
        @RequestParam("pageNo") Integer pageNo,
        @RequestParam("pageSize") Integer pageSize
    ) {
        String jwtToken = AuthUtil.getJwtToken();

        var uriBuilder= UriComponentsBuilder
            .fromHttpUrl(serviceUrlConfig.teamServiceUrl())
            .path("/team")
            .queryParam("pageNo", pageNo)
            .queryParam("pageSize", pageSize);
        URI uri = uriBuilder.build().toUri();

        restClient.get()
            .uri(uri)
            .headers(h->h.setBearerAuth(jwtToken))
            .retrieve()
            .toBodilessEntity();
        return ResponseEntity.ok().build();
    }
}
