package task.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    private Long kgs;
    private Long kzt;
    private Long rub;
    private Long usd;
    private Long eur;
    private Long rmb;
}
