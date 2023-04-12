module jmp.application {
    uses org.example.jmp.bank.api.Bank;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
}