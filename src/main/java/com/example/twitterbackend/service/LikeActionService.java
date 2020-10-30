package com.example.twitterbackend.service;

import com.example.twitterbackend.dto.LikeActionDto;
import com.example.twitterbackend.exceptions.TweetNotFoundException;
import com.example.twitterbackend.exceptions.TwitterException;
import com.example.twitterbackend.model.LikeAction;
import com.example.twitterbackend.model.Tweet;
import com.example.twitterbackend.repository.LikeRepository;
import com.example.twitterbackend.repository.TweetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.twitterbackend.model.LikeType.LIKE;

@Service
@AllArgsConstructor
public class LikeActionService {

    private final TweetRepository tweetRepository;
    private final AuthService authService;
    private final LikeRepository likeRepository;

    @Transactional
    public void likeAction(LikeActionDto likeActionDto) {
        Tweet tweet = tweetRepository.findById(likeActionDto.getTweetId()).
                orElseThrow(()->new TweetNotFoundException("Tweet not found with ID - " + likeActionDto.getTweetId()));
        Optional<LikeAction> likeActionByTweetAndUser = likeRepository.findTopByTweetAndUserOrderByLikeIdDesc(tweet, authService.getCurrentUser());

        if (likeActionByTweetAndUser.isPresent() &&
                likeActionByTweetAndUser.get().getLikeType()
                        .equals(likeActionDto.getLikeType())) {
            throw new TwitterException("You have already "
                    + likeActionDto.getLikeType() + "'d for this post");
        }
        if (LIKE.equals(likeActionDto.getLikeType())) {
            tweet.setLikeCount(tweet.getLikeCount() + 1);
        } else {
            if(!likeActionByTweetAndUser.isPresent()) {
                throw new TwitterException("You need to Like a Tweet first to Unlike");
            }
            tweet.setLikeCount(tweet.getLikeCount() - 1);
        }
        likeRepository.save(mapToLikeAction(likeActionDto, tweet));
        tweetRepository.save(tweet);
    }

    private LikeAction mapToLikeAction(LikeActionDto likeActionDto, Tweet tweet) {
        return LikeAction.builder()
                .likeType(likeActionDto.getLikeType())
                .tweet(tweet)
                .user(authService.getCurrentUser())
                .build();
    }
}
