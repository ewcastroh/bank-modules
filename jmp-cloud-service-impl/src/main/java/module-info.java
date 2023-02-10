module jmp.cloud.service.impl {
    requires transitive jmp.service.api;
    requires jmp.bank.dto;
    requires java.sql;
    exports com.ewch.modules.jmp.cloud.service.impl;
    exports com.ewch.modules.jmp.cloud.service.impl.database;
    provides com.ewch.modules.jmp.service.api.CloudService with com.ewch.modules.jmp.cloud.service.impl.CloudServiceImpl;
}