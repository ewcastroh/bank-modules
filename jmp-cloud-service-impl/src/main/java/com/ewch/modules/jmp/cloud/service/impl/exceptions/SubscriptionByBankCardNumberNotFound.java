package com.ewch.modules.jmp.cloud.service.impl.exceptions;

public class SubscriptionByBankCardNumberNotFound extends RuntimeException {
    public SubscriptionByBankCardNumberNotFound() {
        super();
    }

    public SubscriptionByBankCardNumberNotFound(String message) {
        super(message);
    }

}
