package com.ewch.modules.jmp.service.api;

import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.Subscription;
import com.ewch.modules.jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface CloudService {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String bankCardNumber);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()))
                .average()
                .orElse(Double.NaN);
    }

    static boolean isPayableUser(User user) {
        return ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now()) > 18;
    }

    static List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> subscriptionPredicate) {
        return new ArrayList<>();
    }

    User createUser(User user);

    Subscription createSubscription(Subscription subscription);

    List<Object> getAllBankCards();

    List<Object> getAllSubscriptions();
}
