package com.example.twitterbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class LikeAction {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long likeId;
    private LikeType likeType;
    @NotNull
    @ManyToOne (fetch = LAZY)
    @JoinColumn(name = "tweetId", referencedColumnName = "tweetId")
    private Tweet tweet;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
