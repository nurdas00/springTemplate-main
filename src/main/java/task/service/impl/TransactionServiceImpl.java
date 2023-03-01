package task.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import task.Exception.TransactionException;
import task.dao.BalanceDao;
import task.entity.Transaction;
import task.entity.User;
import task.enums.TransactionStatus;
import task.repository.TransactionRepository;
import task.repository.UserRepository;
import task.service.TransactionService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        log.info("created transaction from : " + transaction.getSender().getName() + " "
                + transaction.getSender().getLastname());
    }

    @Override
    @Transactional
    public void commitTransaction(Transaction transaction, Long id) {
        checkTransaction(transaction, id);

        log.info("committing transaction with id: " + transaction.getId());

        balanceDao.subtractFromBalance(transaction.getSender().getId(), transaction.getAmount(), transaction.getCurrency());
        balanceDao.addToBalance(transaction.getReceiver().getId(), transaction.getAmount(), transaction.getCurrency());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        log.info("Transaction successfully completed");
    }

    @Override
    public List<Transaction> getUserAsReceiverTransactions(User user) {
        List<Transaction> transactions = (List<Transaction>) transactionRepository.findAll();

        return transactions.stream()
                .filter(t -> Objects.equals(t.getReceiver().getName(), user.getName())
                        && Objects.equals(t.getReceiver().getLastname(), user.getLastname()))
                .collect(Collectors.toList());
    }

    @Override
    public Transaction getTransactionById(Long id) {
        Optional<Transaction> transaction =transactionRepository.findById(id);
        if(transaction.isPresent()) {
            return transaction.get();
        }
        throw new TransactionException("no transaction with id:" + id);
    }

    private void setTransactionFields(Transaction transaction) {
        User receiver = transaction.getReceiver();

        try {
            receiver.setId(userRepository.findUserByNameAndLastname(receiver.getName(), receiver.getLastname()).getId());
        } catch (Exception e) {
            throw new TransactionException("couldn't find receiver");
        }

        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        transaction.setConfirmationCode(code);

        transaction.setStatus(TransactionStatus.CREATED);
    }

    private void checkTransaction(Transaction transaction, Long id) {
        String code = transaction.getConfirmationCode();
        Transaction transactionByConfirmationCode = transactionRepository.findByConfirmationCode(code);
        if (transactionByConfirmationCode == null) {
            throw new TransactionException("Couldn't find confirmation code");
        }

        if(!Objects.equals(transactionByConfirmationCode.getId(), id)){
            throw new TransactionException("You picked wrong transaction");
        }

        if (transactionByConfirmationCode.getStatus() == TransactionStatus.COMPLETED) {
            throw new TransactionException("Transaction is already completed");
        }
    }
}
