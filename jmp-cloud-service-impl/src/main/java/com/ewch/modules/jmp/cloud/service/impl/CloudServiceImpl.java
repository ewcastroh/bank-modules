package com.ewch.modules.jmp.cloud.service.impl;

import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;
import com.ewch.modules.jmp.service.api.CloudService;

import java.util.List;
import java.util.Optional;

public class CloudServiceImpl implements CloudService {
    @Override
    public void subscribe(BankCard bankCard) {

    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
