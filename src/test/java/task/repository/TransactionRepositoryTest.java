package task.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import task.entity.Transaction;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Sql({"/sql/create-data-for-test.sql"})
public class TransactionRepositoryTest {

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void shouldReturnTransactionByConfirmationCode() {
        Transaction transaction = transactionRepository.findByConfirmationCode("abc123");

        assertNotNull(transaction);
    }
}
