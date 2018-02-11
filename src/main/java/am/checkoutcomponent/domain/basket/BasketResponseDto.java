package am.checkoutcomponent.domain.basket;

import am.checkoutcomponent.domain.warehouse.WarehouseConfirmationDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BasketResponseDto {

    private WarehouseConfirmationDto orderConfirmation;
    private BasketDto basketDto;

}
