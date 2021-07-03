package com.austral.jibberjabberposts.clients;


import com.austral.jibberjabberposts.dto.users.CreateUserDto;
import com.austral.jibberjabberposts.dto.users.FollowingIdsDto;
import com.austral.jibberjabberposts.dto.users.ReducedUserDto;
import com.austral.jibberjabberposts.dto.users.UserListingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Component
public class UsersClient {
    private final String USER_SERVICE_URL = "http://jibber-jabber-user:8080/users";
    private final RestTemplate restTemplate;

    public UsersClient() {
        this.restTemplate = new RestTemplate();
    }

    public ReducedUserDto getUserById(String userId) {
        String url = USER_SERVICE_URL + "/" + userId;
        ResponseEntity<ReducedUserDto> response = restTemplate.getForEntity(url, ReducedUserDto.class);
        return response.getBody();
    }

    public FollowingIdsDto getFollowingUsersIds(String loggedUserId) {
        String url = USER_SERVICE_URL + "/get-following-ids/" + loggedUserId;
        ResponseEntity<FollowingIdsDto> response = restTemplate.getForEntity(url, FollowingIdsDto.class);
        return response.getBody();
    }
}
