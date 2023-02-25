package task.dao;

import task.entity.User;
import task.enums.Currency;

public interface UserDao {

    void subtractFromBalance(Long id, Long amount, Currency currency);
    void addToBalance(Long id, Long amount, Currency currency);
    User findByUsername(String username);
    Long findIdByFIO(String name, String lastname);
}
