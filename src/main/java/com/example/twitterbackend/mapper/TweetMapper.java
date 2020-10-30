package com.example.twitterbackend.mapper;

import com.example.twitterbackend.dto.TweetRequest;
import com.example.twitterbackend.dto.TweetResponse;
import com.example.twitterbackend.model.LikeAction;
import com.example.twitterbackend.model.LikeType;
import com.example.twitterbackend.model.Tweet;
import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.LikeRepository;
import com.example.twitterbackend.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.example.twitterbackend.model.LikeType.LIKE;
import static com.example.twitterbackend.model.LikeType.UNLIKE;

@Mapper(componentModel = "spring")
public abstract class TweetMapper {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "postTime", expression = "java(java.time.Instant.now())")
    @Mapping(target = "content", source = "tweetRequest.content")
    @Mapping(target = "likeCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Tweet map(TweetRequest tweetRequest, User user);


    @Mapping(target = "id", source = "tweetId")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "liked", expression = "java(isTweetLiked(tweet))")
    @Mapping(target = "unliked", expression = "java(isTweetUnliked(tweet))")
    public abstract TweetResponse mapToDto(Tweet tweet);

    boolean isTweetLiked(Tweet tweet) {
        return checkLikeType(tweet, LIKE);
    }

    boolean isTweetUnliked(Tweet tweet) {
        return checkLikeType(tweet, UNLIKE);
    }

    private boolean checkLikeType(Tweet tweet, LikeType likeType) {
        if (authService.isLoggedIn()) {
            Optional<LikeAction> voteForPostByUser =
                    likeRepository.findTopByTweetAndUserOrderByLikeIdDesc(tweet,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getLikeType().equals(likeType))
                    .isPresent();
        }
        return false;
    }

}
