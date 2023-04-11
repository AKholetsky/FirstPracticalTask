package org.example.jmp.bank.api;

import org.example.jmp.dto.BankCardType;
import org.example.jmp.dto.User;
import org.example.jmp.dto.BankCard;

public interface Bank {

    BankCard createBankCard(User user, BankCardType cardType);


}
