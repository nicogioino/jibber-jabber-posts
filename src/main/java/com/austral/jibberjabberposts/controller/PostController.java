package com.austral.jibberjabberposts.controller;

import com.austral.jibberjabberposts.dto.PostCreationDto;
import com.austral.jibberjabberposts.dto.PostInfoDto;
import com.austral.jibberjabberposts.dto.PostListingDto;
import com.austral.jibberjabberposts.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/get-all/{loggedUserId}")
    public PostListingDto postListingDto(@PathVariable String loggedUserId) {
        return postService.getAllPosts(loggedUserId);
    }

    @PostMapping("/create")
    public PostInfoDto createPost (@RequestBody PostCreationDto postCreationDto, @RequestHeader("userId") String userId) {
        return postService.createPost(postCreationDto,userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @GetMapping("/by-user/{userId}")
    public PostListingDto getPostsByUserId(@PathVariable String userId) {
        return postService.findAllByCreatorId(userId);
    }
}
