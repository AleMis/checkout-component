package am.checkoutcomponent.domain.discount;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreatedDiscountDto {

    private boolean wasDiscountCreated;
    private DiscountDto discountItemRequestDto;
}
