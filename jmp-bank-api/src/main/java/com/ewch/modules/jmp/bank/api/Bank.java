package com.ewch.modules.jmp.bank.api;

import com.ewch.modules.jmp.dto.BankCard;
import com.ewch.modules.jmp.dto.BankCardType;
import com.ewch.modules.jmp.dto.User;

public interface Bank {

    BankCard createBankCard(User user, BankCardType bankCardType);
}
