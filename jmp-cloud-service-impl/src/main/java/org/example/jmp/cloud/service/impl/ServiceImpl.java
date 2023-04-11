package org.example.jmp.cloud.service.impl;

import org.example.jmp.dto.BankCard;
import org.example.jmp.dto.Subscription;
import org.example.jmp.dto.User;
import org.example.jmp.service.api.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class ServiceImpl implements Service {

    private final Map<User, List<Subscription>> subscriptions = new HashMap<>();
    @Override
    public void subscribe(BankCard bankCard) {
        final var user = bankCard.getUser();
        final var number = bankCard.getNumber();

        final var subscription = new Subscription(number, LocalDate.now());

        subscriptions.computeIfAbsent(user, u -> new LinkedList<>()).add(subscription);
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String number) {
        return subscriptions.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(subscription -> subscription.number().equals(number))
                .findAny();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(subscriptions.keySet());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return subscriptions.values()
                .stream().flatMap(Collection::stream).filter(predicate)
                .toList();
    }


}
