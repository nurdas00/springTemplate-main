package task.service;

import task.entity.Transaction;
import task.entity.User;

import java.util.List;

public interface TransactionService {

    void createTransasction(Transaction transaction);
    void commitTransaction(Transaction transaction, Long id);
    List<Transaction> getUserAsReceiverTransactions(User user);
    Transaction getTransactionById(Long id);
}
