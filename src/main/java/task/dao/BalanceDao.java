package task.dao;

import task.enums.Currency;

public interface BalanceDao {

    void subtractFromBalance(Long userId, Long amount, Currency currency);
    void addToBalance(Long userId, Long amount, Currency currency);
    void checkBalance(Long userId, Long amount, Currency currency);
}
