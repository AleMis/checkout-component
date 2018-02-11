package am.checkoutcomponent.domain.discount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discount_id")
    private Long id;

    @Column(name = "product_individual_number")
    private String productIndividualNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "units")
    private Integer units;

    @Column(name = "special_price")
    private BigDecimal specialPrice;

    @Column(name = "since")
    @Temporal(TemporalType.DATE)
    private Calendar since;

    @Column(name = "until")
    @Temporal(TemporalType.DATE)
    private Calendar until;
}
