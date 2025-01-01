package com.star.altineller_kuyumcu.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final MessageType messageType;

    public BaseException(MessageType messageType) {
        super(messageType.getMessage());
        this.messageType = messageType;
    }
}
