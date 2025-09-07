package meetingteam.notificationservice.services;

import java.util.List;

import meetingteam.notificationservice.dtos.User.ResUserDto;

public interface UserService {
    List<ResUserDto> getUsersByIds(List<String> userIds);
}
