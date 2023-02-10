module jmp.cloud.bank.impl {
    requires transitive jmp.bank.api;
    requires jmp.bank.dto;
    exports com.ewch.modules.jmp.cloud.bank.impl;
    //provides com.ewch.modules.jmp.cloud.bank.Bank with com.ewch.modules.jmp.cloud.bank.impl.BankImpl;
}