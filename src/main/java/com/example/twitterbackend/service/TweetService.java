package com.example.twitterbackend.service;

import com.example.twitterbackend.dto.TweetRequest;
import com.example.twitterbackend.dto.TweetResponse;
import com.example.twitterbackend.exceptions.TweetNotFoundException;
import com.example.twitterbackend.mapper.TweetMapper;
import com.example.twitterbackend.model.Tweet;
import com.example.twitterbackend.model.User;
import com.example.twitterbackend.repository.TweetRepository;
import com.example.twitterbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final AuthService authService;
    private final UserRepository userRepository;
    public void save(TweetRequest tweetRequest) {
        tweetRepository.save(tweetMapper.map(tweetRequest, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public TweetResponse getTweet(Long id) {
        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new TweetNotFoundException(id.toString()));
        return tweetMapper.mapToDto(tweet);
    }

    @Transactional(readOnly = true)
    public List<TweetResponse> getFeed() {
        Set<String> following = authService.getCurrentUser().getFollowing();
        List<User> userList = userRepository.findByUsernameIn(following);
        return tweetRepository.findByUserIn(userList)
                .stream()
                .map(tweetMapper::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public List<TweetResponse> getTweetsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return tweetRepository.findByUser(user)
                .stream()
                .map(tweetMapper::mapToDto)
                .collect(toList());
    }
}
