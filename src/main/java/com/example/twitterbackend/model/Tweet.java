package com.example.twitterbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tweetId;

    private Instant postTime;


    @ManyToOne
    @JoinColumn(referencedColumnName = "userId")
    private User user;

    @NotNull
    @Size(max = 140, message = "Length of tweet cannot be more than 140")
    private String content;

    private Integer likeCount = 0;

}