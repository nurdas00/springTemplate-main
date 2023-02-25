package task.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import task.dao.UserDao;
import task.entity.Transaction;
import task.entity.User;
import task.enums.TransactionStatus;
import task.repository.TransactionRepository;
import task.service.TransactionService;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private static int counter = 1;

    private final TransactionRepository transactionRepository;
    private final UserDao userDao;

    @Override
    @Transactional
    public void createTransasction(Transaction transaction) {
        String code = "abc" + counter;

        transaction.setStatus(TransactionStatus.NEW);
        transaction.setConfirmationCode(code);
        counter++;

        User receiver = transaction.getReceiver();
        User sender = transaction.getSender();

        receiver.setId(userDao.findIdByFIO(receiver.getName(), receiver.getLastname()));
        sender.setId(userDao.findIdByFIO(sender.getName(), sender.getLastname()));

        transaction.setStatus(TransactionStatus.PROCESSING);
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void commitTransaction(User user) {
        String code = user.getConfirmationCode();
        Transaction transaction = transactionRepository.findByConfirmationCode(code);

        log.info("committing transaction with id: " + transaction.getId());

        userDao.subtractFromBalance(transaction.getSender().getId(), transaction.getAmount(), transaction.getCurrency());
        userDao.addToBalance(transaction.getReceiver().getId(), transaction.getAmount(), transaction.getCurrency());

        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
    }
}
