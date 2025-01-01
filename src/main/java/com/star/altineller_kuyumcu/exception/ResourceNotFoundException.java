package com.star.altineller_kuyumcu.exception;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(MessageType messageType) {
        super(messageType);
    }
}