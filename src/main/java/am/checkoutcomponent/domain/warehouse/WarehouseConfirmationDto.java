package am.checkoutcomponent.domain.warehouse;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WarehouseConfirmationDto {

    private boolean productAvailable;
    private String information;
}
