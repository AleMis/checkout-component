package am.checkoutcomponent.domain.discount;
import lombok.*;

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
@ToString
public class DiscountDto {

    private Long id;
    private String productIndividualNumber;
    private String description;
    private Integer units;
    private BigDecimal specialPrice;
    private Calendar since;
    private Calendar until;
}
