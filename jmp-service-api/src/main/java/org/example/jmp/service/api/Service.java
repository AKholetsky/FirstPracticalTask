package org.example.jmp.service.api;

import org.example.jmp.dto.BankCard;
import org.example.jmp.dto.Subscription;
import org.example.jmp.dto.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Service {

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String number);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        return getAllUsers().stream().mapToLong(user -> ChronoUnit.YEARS.between(user.birthday(), LocalDate.now())).average().orElse(0);
    }

    static boolean isPayableUser(User user) {
        return user.birthday().isBefore(LocalDate.now().minusYears(18));
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);
}
