package task.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import task.Exception.TransactionException;
import task.dao.BalanceDao;
import task.entity.Transaction;
import task.entity.User;
import task.enums.TransactionStatus;
import task.repository.UserRepository;
import task.repository.TransactionRepository;
import task.service.TransactionService;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BalanceDao balanceDao;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void createTransasction(Transaction transaction) {
        setTransactionFields(transaction);

        balanceDao.checkBalance(transaction.getSender().getId(), transaction.getAmount(), transaction.getCurrency());

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void commitTransaction(User user) {
        Transaction transaction = getTransaction(user);

        log.info("committing transaction with id: " + transaction.getId());

        balanceDao.subtractFromBalance(transaction.getSender().getId(), transaction.getAmount(), transaction.getCurrency());
        balanceDao.addToBalance(transaction.getReceiver().getId(), transaction.getAmount(), transaction.getCurrency());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
    }

    private void setTransactionFields(Transaction transaction) {
        User receiver = transaction.getReceiver();
        User sender = transaction.getSender();

        receiver.setId(userRepository.findUserByNameAndLastname(receiver.getName(), receiver.getLastname()).getId());
        sender.setId(userRepository.findUserByNameAndLastname(sender.getName(), sender.getLastname()).getId());

        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        transaction.setConfirmationCode(code);

        transaction.setStatus(TransactionStatus.CREATED);
    }

    private Transaction getTransaction(User user) {
        String code = user.getConfirmationCode();
        Transaction transaction;

        try {
            transaction = transactionRepository.findByConfirmationCode(code);
        } catch (Exception e) {
            throw new TransactionException("Couldn't find confirmation code");
        }

        if (!transaction.getReceiver().getName().equalsIgnoreCase(user.getName()) &&
                !transaction.getReceiver().getLastname().equalsIgnoreCase(user.getLastname())) {
            throw new TransactionException("This code doesn't belong to you");
        }

        if (transaction.getStatus() == TransactionStatus.COMPLETED) {
            throw new TransactionException("Transaction is already completed");
        }

        return transaction;
    }
}
