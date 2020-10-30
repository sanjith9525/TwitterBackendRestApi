package com.example.twitterbackend.repository;

import com.example.twitterbackend.model.LikeAction;
import com.example.twitterbackend.model.Tweet;
import com.example.twitterbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeAction, Long> {
    Optional<LikeAction> findTopByTweetAndUserOrderByLikeIdDesc(Tweet tweet, User currentUser);
}
