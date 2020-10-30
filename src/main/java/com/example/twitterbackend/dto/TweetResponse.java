package com.example.twitterbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetResponse {
    private Long id;
    private String content;
    private String userName;
    private Integer likeCount;
    private boolean liked;
    private boolean unliked;
}