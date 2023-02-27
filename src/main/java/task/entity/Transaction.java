package task.entity;


import lombok.Getter;
import lombok.Setter;
import task.enums.Currency;
import task.enums.TransactionStatus;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String confirmationCode;

    private String comment;
}
