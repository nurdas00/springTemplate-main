package task.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Transaction findByConfirmationCode(String code);
}
