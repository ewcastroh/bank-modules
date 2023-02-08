package com.ewch.modules.jmp.service.api;

import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;

import java.util.List;
import java.util.Optional;

public interface CloudService {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber);

    List<User> getAllUsers();

}
