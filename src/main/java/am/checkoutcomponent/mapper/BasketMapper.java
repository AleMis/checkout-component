package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasketMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public BasketDto convertToBasketDto(Basket basket) {
        return modelMapper().map(basket, BasketDto.class);
    }
}
