package com.leetreader.leetReader.exception.user;

public class PasswordMissMatchException extends RuntimeException{
    public PasswordMissMatchException(String message){
        super(message);
    }
}
