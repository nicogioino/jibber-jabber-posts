package com.austral.jibberjabberposts.service.impl;

import com.austral.jibberjabberposts.clients.UsersClient;
import com.austral.jibberjabberposts.dto.PostCreationDto;
import com.austral.jibberjabberposts.dto.PostInfoDto;
import com.austral.jibberjabberposts.dto.PostListingDto;
import com.austral.jibberjabberposts.dto.users.ReducedUserDto;
import com.austral.jibberjabberposts.model.Post;
import com.austral.jibberjabberposts.repository.PostRepository;
import com.austral.jibberjabberposts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UsersClient usersClient;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UsersClient usersClient) {
        this.postRepository = postRepository;
        this.usersClient = usersClient;
    }

    @Override
    public PostListingDto getAllPosts(String loggedUserId) {
        Set<String> followingUsersIds = usersClient.getFollowingUsersIds(loggedUserId).getFollowingUsersIds();
        followingUsersIds.add(loggedUserId); //Makes user able to see also his own posts

        List<Post> posts = postRepository.findAllByCreatorIdIn(followingUsersIds);
        List<PostInfoDto> returnPosts = new ArrayList<>();

        posts.sort(Comparator.comparing(Post::getCreatedTime).reversed());//Orders posts by most recent first
        posts.forEach((post -> {
            ReducedUserDto creatorDto = usersClient.getUserById(post.getCreatorId());
            returnPosts.add(PostInfoDto.from(post,creatorDto));
        }));
        return new PostListingDto(returnPosts);
    }

    @Override
    public PostInfoDto createPost(PostCreationDto postDto, String userId) {
        Post postToSave = Post.builder()
                .creatorId(userId)
                .content(postDto.getContent())
                .build();
        Post savedPost = postRepository.save(postToSave);
        ReducedUserDto creatorDto = usersClient.getUserById(savedPost.getCreatorId());
        return PostInfoDto.from(savedPost,creatorDto);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostListingDto findAllByCreatorId(String creatorId) {
        List<Post> posts = postRepository.findAllByCreatorId(creatorId);
        List<PostInfoDto> returnPosts = new ArrayList<>();
        posts.sort(Comparator.comparing(Post::getCreatedTime).reversed());//Orders posts by most recent first
        posts.forEach((post -> {
            ReducedUserDto creatorDto = usersClient.getUserById(post.getCreatorId());
            returnPosts.add(PostInfoDto.from(post,creatorDto));
        }));
        return new PostListingDto(returnPosts);
    }

    @Override
    public PostInfoDto likePost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Set<String> likedBy = post.getLikedBy();
        Set<String> dislikedBy = post.getDislikedBy();
        dislikedBy.remove(userId);
        likedBy.add(userId);

        post.setLikedBy(likedBy);
        post.setDislikedBy(dislikedBy);

        Post edited = postRepository.save(post);
        ReducedUserDto creatorDto = usersClient.getUserById(edited.getCreatorId());
        return PostInfoDto.from(edited,creatorDto);
    }

    @Override
    public PostInfoDto dislikePost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Set<String> likedBy = post.getLikedBy();
        Set<String> dislikedBy = post.getDislikedBy();
        dislikedBy.add(userId);
        likedBy.remove(userId);

        post.setLikedBy(likedBy);
        post.setDislikedBy(dislikedBy);

        Post edited = postRepository.save(post);
        ReducedUserDto creatorDto = usersClient.getUserById(edited.getCreatorId());
        return PostInfoDto.from(edited,creatorDto);
    }
}
