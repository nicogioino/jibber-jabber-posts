package com.austral.jibberjabberposts.service;

import com.austral.jibberjabberposts.dto.PostCreationDto;
import com.austral.jibberjabberposts.dto.PostInfoDto;
import com.austral.jibberjabberposts.dto.PostListingDto;

import java.util.List;

public interface PostService {

    PostListingDto getAllPosts(String loggedUserId);

    PostInfoDto createPost(PostCreationDto postDto, String userId);

    void deletePost(Long id);

    PostListingDto findAllByCreatorId(String creatorId);
}
