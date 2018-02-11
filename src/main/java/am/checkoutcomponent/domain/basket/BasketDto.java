package am.checkoutcomponent.domain.basket;

import am.checkoutcomponent.domain.basket.product.BasketProduct;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BasketDto {

    private Long id;
    private Long userId;
    private boolean isOpen;
    private BigDecimal basketValue;
    private List<BasketProduct> basketProductList;
}
