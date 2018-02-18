package am.checkoutcomponent.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductCheckoutDto {

    private boolean created;
    private ProductDto productDto;
}
