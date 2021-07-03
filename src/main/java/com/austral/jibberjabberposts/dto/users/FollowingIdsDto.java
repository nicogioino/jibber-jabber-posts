package com.austral.jibberjabberposts.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingIdsDto {
    Set<String> followingUsersIds;
}
