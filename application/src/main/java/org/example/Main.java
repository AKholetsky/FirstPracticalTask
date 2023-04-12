package org.example;

import org.example.jmp.bank.api.Bank;
import org.example.jmp.cloud.bank.impl.BankImpl;
import org.example.jmp.cloud.service.impl.ServiceImpl;
import org.example.jmp.dto.*;
import org.example.jmp.service.api.Service;
import org.example.jmp.service.api.exception.SubscriptionNotFound;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        final Map<BankCardType, BiFunction<String, User, BankCard>> cardsCreators = Map.of(
                BankCardType.CREDIT, CreditBankCard::new,
                BankCardType.DEBIT, DebitBankCard::new
        );

        User user1 = new User("Ivan", "Ivanov", LocalDate.of(1990, Month.APRIL, 1));
        User user2 = new User("Petr", "Petrov", LocalDate.of(2005, Month.AUGUST, 2));
        User user3 = new User("Petr", "Petrov", LocalDate.of(2005, Month.APRIL, 1));
        User user4 = new User("Petr", "Petrov", LocalDate.of(2005, Month.APRIL, 15));

        final var bank = new BankImpl(cardsCreators);
        final BankCard bankCard = bank.createBankCard(user1, BankCardType.DEBIT);

        final BankCard creditBankCard = bank.createBankCard(user2, BankCardType.CREDIT);

        Service bankService = new ServiceImpl();
        bankService.subscribe(bankCard);
        bankService.subscribe(creditBankCard);

        try {
            System.out.println(bankService.getSubscriptionByBankCardNumber(bankCard.getNumber()).orElseThrow(SubscriptionNotFound::new));
            System.out.println(bankService.getSubscriptionByBankCardNumber(creditBankCard.getNumber()).orElseThrow(SubscriptionNotFound::new));
        } catch (SubscriptionNotFound subscriptionNotFound) {
            System.out.println("Subscription not found");
        }

        System.out.println(bankService.getAverageUsersAge());

        System.out.println(Service.isPayableUser(user1));
        System.out.println(Service.isPayableUser(user2));
        System.out.println(Service.isPayableUser(user3));
        System.out.println(Service.isPayableUser(user4));

        bankService.getAllSubscriptionsByCondition(subscription -> subscription.startDate().isBefore(LocalDate.of(2023, 11, 2))).forEach(System.out::println);

        final BankCard creditBankCard1 = bank.createBankCard(user1, CreditBankCard::new);
        final BankCard debitBankCard1 = bank.createBankCard(user1, DebitBankCard::new);

        System.out.println(creditBankCard1);
        System.out.println(debitBankCard1);

        final Iterable<Bank> load = ServiceLoader.load(Bank.class);
        final Bank nextBank = load.iterator().next();
        System.out.println("Is BankImpl " + (nextBank instanceof BankImpl));
        System.out.println(nextBank.createBankCard(user2, CreditBankCard::new).getNumber());
    }
}