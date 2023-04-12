package org.example.jmp.bank.api;

import org.example.jmp.dto.BankCardType;
import org.example.jmp.dto.User;
import org.example.jmp.dto.BankCard;

import java.util.function.BiFunction;

public interface Bank {

    BankCard createBankCard(User user, BankCardType cardType);

    BankCard createBankCard(User user, BiFunction<String, User, BankCard> creator);
}
