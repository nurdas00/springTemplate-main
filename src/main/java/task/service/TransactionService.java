package task.service;

import task.entity.Transaction;
import task.entity.User;

public interface TransactionService {

    void createTransasction(Transaction transaction);
    void commitTransaction(User user);
}
