package com.ewch.modules.jmp.cloud.service.impl;

import com.ewch.modules.jmp.cloud.service.impl.database.DBConfig;
import com.ewch.modules.jmp.cloud.service.impl.exceptions.SubscriptionByBankCardNumberNotFound;
import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;
import com.ewch.modules.jmp.service.api.CloudService;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CloudServiceImpl implements CloudService {

    private final DBConfig dbConfig;

    public CloudServiceImpl() {
        dbConfig = null;
    }

    public CloudServiceImpl(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    @Override
    public void subscribe(BankCard bankCard) {

    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber) {
        return Optional.ofNullable(Optional.ofNullable(dbConfig.getSubscriptionByBankCardNumber(bankCardNumber))
                .orElseThrow(() -> new SubscriptionByBankCardNumberNotFound("Subscription not found.")));
    }

    @Override
    public List<User> getAllUsers() {
        return dbConfig.getUsers()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> subscriptionPredicate) {
        return getAllSubscriptions()
                .stream()
                .filter(subscriptionPredicate)
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        return dbConfig.createUser(user);
    }

    @Override
    public BankCard createBankCard(BankCard bankCard) {
        return dbConfig.createBankCard(bankCard);
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
        return dbConfig.createSubscription(subscription);
    }

    @Override
    public List<BankCard> getAllBankCards() {
        return dbConfig.getAllBankCards()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return dbConfig.getAllSubscriptions()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }
}
