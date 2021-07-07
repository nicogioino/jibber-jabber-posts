package com.austral.jibberjabberposts.dto;

import com.austral.jibberjabberposts.dto.users.ReducedUserDto;
import com.austral.jibberjabberposts.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoDto {

    private Long id;

    LocalDateTime createdTime;

    String content;

    int likes;

    int dislikes;

    ReducedUserDto reducedUserDto;

    public static PostInfoDto from(Post post, ReducedUserDto reducedUserDto) {
        return new PostInfoDto(
                post.getId(),
                post.getCreatedTime(),
                post.getContent(),
                post.getLikedBy().size(),
                post.getDislikedBy().size(),
                reducedUserDto
        );
    }
}
