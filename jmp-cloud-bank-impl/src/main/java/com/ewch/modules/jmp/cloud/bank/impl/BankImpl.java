package com.ewch.modules.jmp.cloud.bank.impl;

import com.ewch.modules.jmp.bank.api.Bank;
import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.BankCardType;
import com.ewch.modules.jmp.dto.CreditBankCard;
import com.ewch.modules.jmp.dto.DebitBankCard;
import com.ewch.modules.jmp.dto.User;

import java.util.Random;

public class BankImpl implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        if (user == null || bankCardType == null) {
            return null;
        }
        String cardNumber = getRandomCardNumber();
        switch (bankCardType) {
            case CREDIT -> {
                return new CreditBankCard(cardNumber, user);
            }
            case DEBIT -> {
                return new DebitBankCard(cardNumber, user);
            }
            default -> throw new IllegalArgumentException("Unknown BankCardType " + bankCardType);
        }
    }

    private static String getRandomCardNumber() {
        var numberOfDigits = 16;
        var random = new Random();
        StringBuilder sb = new StringBuilder(numberOfDigits);
        for (var i = 0; i < numberOfDigits; i++) {
            sb.append((char) ('0' + random.nextInt(10)));
        }
        return sb.toString();
    }
}
