package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.discount.Discount;
import am.checkoutcomponent.domain.discount.DiscountDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class DiscountMapper {

    public DiscountDto convertToDiscountDto(Discount discount) {
        return new DiscountDto(discount.getId(), discount.getProductIndividualNumber(), discount.getDescription(), discount.getUnits(), discount.getSpecialPrice(), discount.getSince(), discount.getUntil());
    }

    public Discount convertToDiscount(DiscountDto discountDto) {
        return new Discount(discountDto.getId(), discountDto.getProductIndividualNumber(), discountDto.getDescription(), discountDto.getUnits(), discountDto.getSpecialPrice(), discountDto.getSince(), discountDto.getUntil());
    }

    public List<DiscountDto> convertToDiscountDtoList(List<Discount> discountList) {
        return discountList.stream().map(discount -> new DiscountDto(discount.getId(), discount.getProductIndividualNumber(), discount.getDescription(), discount.getUnits(), discount.getSpecialPrice(), discount.getSince(), discount.getUntil())).collect(Collectors.toList());
    }
}
