package task.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import task.entity.Transaction;
import task.entity.User;
import task.service.TransactionService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;

    @PostMapping(path = "/send", consumes = "application/json")
    public ResponseEntity<?> sendMoney(@RequestBody Transaction transaction) {
        try {
            transactionService.createTransasction(transaction);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("created transaction from : " + transaction.getSender().getName() + " "
                + transaction.getSender().getLastname());

        return new ResponseEntity<>(transaction.getConfirmationCode(), HttpStatus.OK);
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receiveMoney(@RequestBody User user) {
        try {
            transactionService.commitTransaction(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Transaction successfully completed");
        return new ResponseEntity<>("Transaction completed", HttpStatus.OK);
    }
}
