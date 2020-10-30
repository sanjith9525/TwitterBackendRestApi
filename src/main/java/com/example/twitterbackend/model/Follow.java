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
public class Follow {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long followId;
    private FollowType followType;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "followerUserId", referencedColumnName = "userId")
    private User user1;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "followingUserId", referencedColumnName = "userId")
    private User user2;
}
