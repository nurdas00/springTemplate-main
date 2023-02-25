package task.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import task.entity.Transaction;
import task.entity.User;
import task.enums.Currency;
import task.enums.TransactionStatus;
import task.service.TransactionService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;

    @PostMapping(path = "/send")
    public ResponseEntity<String> sendMoney(@RequestBody Transaction transaction) {
        log.info("i am on back");

        transactionService.createTransasction(transaction);

        log.info("created transaction from : " + transaction.getId());

        return new ResponseEntity<>(transaction.getConfirmationCode(), HttpStatus.OK);
    }

    @PostMapping("/receive")
    public String receiveMoney(@RequestBody User user) {
        transactionService.commitTransaction(user);

        return "ok";
    }
}
