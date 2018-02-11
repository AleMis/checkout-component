package am.checkoutcomponent.domain.product;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ProductDto {

    private Long id;
    private String name;
    private String individualNumber;
    private String description;
    private String manufacturer;
    private BigDecimal basePrice;
}
