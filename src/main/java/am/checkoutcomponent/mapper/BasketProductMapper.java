package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.basket.product.BasketProduct;
import am.checkoutcomponent.domain.basket.product.BasketProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasketProductMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public BasketProduct convertToBasketProduct(BasketProductDto basketProductDto) {
        return modelMapper().map(basketProductDto, BasketProduct.class);
    }

    public BasketProductDto convertToBasketProduct(BasketProduct basketProduct) {
        return modelMapper().map(basketProduct, BasketProductDto.class);
    }
}
