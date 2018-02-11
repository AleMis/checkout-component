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

    private boolean wasCreated;
    private ProductDto productDto;
}
