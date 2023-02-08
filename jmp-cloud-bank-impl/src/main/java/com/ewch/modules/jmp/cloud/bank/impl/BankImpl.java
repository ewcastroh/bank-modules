package com.ewch.modules.jmp.cloud.bank.impl;

import com.ewch.modules.jmp.bank.api.Bank;
import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.BankCardType;
import com.ewch.modules.jmp.dto.CreditBankCard;
import com.ewch.modules.jmp.dto.DebitBankCard;
import com.ewch.modules.jmp.dto.User;

public class BankImpl implements Bank {

    @Override
    public BankCard createBankCard(User user, BankCardType bankCardType) {
        if (user == null || bankCardType == null) {
            return null;
        }
        int cardNumber = Integer.parseInt(String.valueOf(Math.random()));
        switch (bankCardType) {
            case CREDIT -> {
                return new CreditBankCard(Integer.toString(cardNumber), user);
            }
            case DEBIT -> {
                return new DebitBankCard(Integer.toString(cardNumber), user);
            }
            default -> throw new IllegalArgumentException("Unknown BankCardType " + bankCardType);
        }
        return null;
    }
}
