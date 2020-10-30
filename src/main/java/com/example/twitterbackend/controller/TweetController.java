package com.example.twitterbackend.controller;

import com.example.twitterbackend.dto.TweetRequest;
import com.example.twitterbackend.dto.TweetResponse;
import com.example.twitterbackend.service.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/tweets")
@AllArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @PostMapping
    ResponseEntity<Void> createTweet(@RequestBody TweetRequest tweetRequest) {
        tweetService.save(tweetRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TweetResponse>> getFeed() {
        return status(HttpStatus.OK).body(tweetService.getFeed());
    }

    @GetMapping("id/{id}")
    public ResponseEntity<TweetResponse> getTweet(@PathVariable Long id) {
        return status(HttpStatus.OK).body(tweetService.getTweet(id));
    }

    @GetMapping("by-user/{username}")
    public ResponseEntity<List<TweetResponse>> getTweetsByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(tweetService.getTweetsByUsername(username));
    }
}
