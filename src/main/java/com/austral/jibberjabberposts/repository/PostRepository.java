package com.austral.jibberjabberposts.repository;

import com.austral.jibberjabberposts.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByCreatorId(String creatorId);

    List<Post> findAllByCreatorIdIn(Set<String> ids);
}
