package com.example.twitterbackend.service;

import com.example.twitterbackend.dto.FollowDto;
import com.example.twitterbackend.exceptions.TwitterException;
import com.example.twitterbackend.model.Follow;
import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.FollowRepository;
import com.example.twitterbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.twitterbackend.model.FollowType.FOLLOW;

@Service
@AllArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final FollowRepository followRepository;

    @Transactional
    public void follow(FollowDto followDto) {
        User followedUser = userRepository.findById(followDto.getUserId()).
                orElseThrow(()->new TwitterException("User not found with ID - " + followDto.getUserId()));
        if(followedUser == authService.getCurrentUser()) {
            throw new TwitterException("You cannot follow yourself");
        }
        Optional<Follow> followByUser1AndUser2 = followRepository.findTopByUser1AndUser2OrderByFollowIdDesc(authService.getCurrentUser(), followedUser);


        if (followByUser1AndUser2.isPresent() &&
                followByUser1AndUser2.get().getFollowType()
                        .equals(followDto.getFollowType())) {
            throw new TwitterException("You have already "
                    + followDto.getFollowType() + "'d this person");
        }
        if (FOLLOW.equals(followDto.getFollowType())) {
            authService.getCurrentUser().getFollowing().add(followedUser.getUsername());
            authService.getCurrentUser().setFollowing_count(
                    authService.getCurrentUser().getFollowing_count()+1);
            followedUser.setFollower_count(followedUser.getFollower_count()+1);

        } else {
            if(!followByUser1AndUser2.isPresent()) {
                throw new TwitterException("You need to Follow a User first to Unfollow");
            }
            authService.getCurrentUser().getFollowing().remove(followedUser.getUsername());
            authService.getCurrentUser().setFollowing_count(
                    authService.getCurrentUser().getFollowing_count()-1);
            followedUser.setFollower_count(followedUser.getFollower_count()-1);
        }
        followRepository.save(mapToFollow(followDto, followedUser));
        userRepository.save(followedUser);
        userRepository.save(authService.getCurrentUser());
    }

    private Follow mapToFollow(FollowDto followDto, User followedUser) {
        return Follow.builder()
                .followType(followDto.getFollowType())
                .user1(authService.getCurrentUser())
                .user2(followedUser)
                .build();
    }
}
