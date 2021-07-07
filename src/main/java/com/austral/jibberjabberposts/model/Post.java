package com.austral.jibberjabberposts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    LocalDateTime createdTime;

    String content;

    String creatorId;

    @ElementCollection
    Set<String> likedBy;

    @ElementCollection
    Set<String> dislikedBy;

    public Set<String> getLikedBy() {
        if (likedBy == null) likedBy = new HashSet<>();
        return likedBy;
    }

    public void setLikedBy(Set<String> likedBy) {
        this.likedBy = likedBy;
    }

    public Set<String> getDislikedBy() {
        if (dislikedBy == null) dislikedBy = new HashSet<>();
        return dislikedBy;
    }

    public void setDislikedBy(Set<String> dislikedBy) {
        this.dislikedBy = dislikedBy;
    }
}
