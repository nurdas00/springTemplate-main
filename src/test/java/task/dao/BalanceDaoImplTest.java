package task.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import task.exception.TransactionException;
import task.enums.Currency;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Sql({"/sql/create-data-for-test.sql"})
public class BalanceDaoImplTest {

    @Autowired
    private BalanceDao balanceDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    public void shouldAddToBalance() {
        balanceDao.addToBalance(1L, 100L, Currency.KGS);

        Long balance = (Long) entityManager.createQuery("select kgs from Balance b where user_id = 1").getSingleResult();

        assertEquals(balance, 1100);
    }

    @Test
    @Transactional
    public void shouldSubtractFromBalance() {
        balanceDao.subtractFromBalance(1L, 100L, Currency.KGS);

        Long balance = (Long) entityManager.createQuery("select kgs from Balance b where user_id = 1").getSingleResult();

        assertEquals(balance, 900);
    }

    @Test
    @Transactional
    public void shouldFailCheckBalance() {
        assertThrows(TransactionException.class, () -> balanceDao.checkBalance(1L, 2000L, Currency.KGS));
    }
}
