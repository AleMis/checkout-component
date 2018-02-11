package am.checkoutcomponent.domain.basket.product;

import am.checkoutcomponent.domain.basket.Basket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BasketProductDto {

    private Long id;
    private String productIndividualNumber;
    private Integer units;
    private BigDecimal price;
    private Basket basket;

}
