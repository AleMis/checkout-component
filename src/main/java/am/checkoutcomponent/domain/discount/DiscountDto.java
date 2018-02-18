package am.checkoutcomponent.domain.discount;
import lombok.*;
import java.math.BigDecimal;
import java.util.Calendar;


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
