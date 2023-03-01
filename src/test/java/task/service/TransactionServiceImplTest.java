package task.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import task.dao.BalanceDao;
import task.entity.Transaction;
import task.entity.User;
import task.enums.TransactionStatus;
import task.repository.TransactionRepository;
import task.repository.UserRepository;
import task.service.impl.TransactionServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private BalanceDao balanceDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldCreateTransaction() {
        User sender = new User();
        sender.setId(1L);
        sender.setName("tom");
        sender.setLastname("reddle");

        User receiver = new User();
        receiver.setId(2L);
        receiver.setName("harry");
        receiver.setLastname("potter");

        doReturn(sender).when(userRepository).findUserByNameAndLastname("tom", "reddle");
        doReturn(sender).when(userRepository).findUserByNameAndLastname("harry", "potter");

        UUID uuid = UUID.randomUUID();
        Mockito.mockStatic(UUID.class);
        when(UUID.randomUUID()).thenReturn(uuid);

        doNothing().when(balanceDao).checkBalance(anyLong(), anyLong(), any());

        doReturn(new Transaction()).when(transactionRepository).save(any());

        Transaction transaction = new Transaction();
        transaction.setAmount(100L);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        doReturn(transaction).when(transactionRepository).findByConfirmationCode(anyString());

        service.createTransasction(transaction);

        assertEquals(transaction.getConfirmationCode(), uuid.toString().replace("-", "").substring(0, 5));
    }

    @Test
    public void shouldCommitTransaction() {
        User sender = new User();
        sender.setId(2L);
        sender.setName("harry");
        sender.setLastname("potter");

        User receiver = new User();
        receiver.setId(1L);
        receiver.setName("tom");
        receiver.setLastname("reddle");

        doNothing().when(balanceDao).subtractFromBalance(anyLong(), anyLong(), any());
        doNothing().when(balanceDao).addToBalance(anyLong(), anyLong(), any());

        doReturn(new Transaction()).when(transactionRepository).save(any());

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100L);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setConfirmationCode("abc123");
        doReturn(transaction).when(transactionRepository).findByConfirmationCode(anyString());

        service.commitTransaction(transaction, 1L);

        assertEquals(transaction.getStatus(), TransactionStatus.COMPLETED);
    }

}
