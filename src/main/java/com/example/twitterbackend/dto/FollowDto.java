package com.example.twitterbackend.dto;

import com.example.twitterbackend.model.FollowType;
import com.example.twitterbackend.model.LikeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {
    private FollowType followType;
    private long userId;
}
