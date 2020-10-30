package com.example.twitterbackend.exceptions;

public class TwitterException extends RuntimeException{
    public TwitterException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
    public TwitterException(String exMessage) {
        super(exMessage);
    }
}

