package org.example.jmp.cloud.bank.impl;

import org.example.jmp.bank.api.Bank;
import org.example.jmp.dto.BankCard;
import org.example.jmp.dto.BankCardType;
import org.example.jmp.dto.User;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

public class BankImpl implements Bank {

    private final Map<BankCardType, BiFunction<String, User, BankCard>> cardCreators;

    public BankImpl(Map<BankCardType, BiFunction<String, User, BankCard>> cardCreators) {
        this.cardCreators = cardCreators;
    }
    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {

        final String cardNumber = UUID.randomUUID().toString();
        return cardCreators.getOrDefault(cardType, (number, u) -> throwIfCardIsUnknown()).apply(cardNumber, user);

    }

    private BankCard throwIfCardIsUnknown() {
        throw new IllegalArgumentException("Card type is unknown");
    }
}
