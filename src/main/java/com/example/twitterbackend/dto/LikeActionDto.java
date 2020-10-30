package com.example.twitterbackend.dto;

import com.example.twitterbackend.model.LikeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeActionDto {
    private LikeType likeType;
    private long tweetId;
}
