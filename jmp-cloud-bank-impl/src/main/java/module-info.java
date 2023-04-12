module jmp.cloud.bank.impl {

    requires transitive jmp.bank.api;

    exports org.example.jmp.cloud.bank.impl;

    provides org.example.jmp.bank.api.Bank with org.example.jmp.cloud.bank.impl.BankImpl;
}