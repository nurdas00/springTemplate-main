package task.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import task.Exception.TransactionException;
import task.dao.BalanceDao;
import task.enums.Currency;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BalanceDaoIml implements BalanceDao {

    private final EntityManager entityManager;

    @Override
    public void subtractFromBalance(Long userId, Long amount, Currency currency){
        Long balance = getBalanceByUserId(userId, currency);

        balance-=amount;
        String query = "update Balance set " + currency.name().toLowerCase() + " = " + balance + " where user_id = :id";

        entityManager. createQuery(query)
                .setParameter("id", userId)
                .executeUpdate();

    }

    @Override
    public void addToBalance(Long userId, Long amount, Currency currency) {
        Long balance = getBalanceByUserId(userId, currency);

        balance+=amount;
        String query = "update Balance set " + currency.name().toLowerCase() + " = " + balance + " where user_id = :id";

        entityManager. createQuery(query)
                .setParameter("id", userId)
                .executeUpdate();
    }

    @Override
    public void checkBalance(Long userId, Long amount, Currency currency) {
        Long balance = getBalanceByUserId(userId, currency);

        if(balance < amount) {
            throw new TransactionException("Insufficient funds");
        }
    }

    private Long getBalanceByUserId(Long id, Currency currency) {
        String query = "select " + currency.name().toLowerCase() + " from Balance where user_id = :id";

        return (Long) entityManager. createQuery(query)
                .setParameter("id", id)
                .getSingleResult();
    }
}
