package task.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import task.dao.UserDao;
import task.entity.User;
import task.enums.Currency;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    @Override
    public void subtractFromBalance(Long id, Long amount, Currency currency){
        String query1 = "select " + currency.name().toLowerCase() + " from Balance where user_id = :id";

        Long balance = (Long) entityManager. createQuery(query1)
                .setParameter("id", id)
                .getSingleResult();

        balance-=amount;
        String query = "update Balance set " + currency.name().toLowerCase() + " = " + balance + " where user_id = :id";

        entityManager. createQuery(query)
                .setParameter("id", id)
                .executeUpdate();

    }

    @Override
    public void addToBalance(Long id, Long amount, Currency currency) {
        String query1 = "select " + currency.name().toLowerCase() + " from Balance where user_id = :id";

        Long balance = (Long) entityManager. createQuery(query1)
                .setParameter("id", id)
                .getSingleResult();

        balance+=amount;
        String query = "update Balance set " + currency.name().toLowerCase() + " = " + balance + " where user_id = :id";

        entityManager. createQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public User findByUsername(String username) {
        return (User) entityManager.createQuery("select u from User u where username = :username")
                .setParameter("username", username).getSingleResult();

    }

    @Override
    public Long findIdByFIO(String name, String lastname) {
        return (Long) entityManager.createQuery("select id from User where name = :name and lastname = :lastname")
                .setParameter("name", name)
                .setParameter("lastname", lastname)
                .getSingleResult();
    }
}
