package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.product.BasketProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketMapperTestSuite {

    @Autowired
    private BasketMapper basketMapper;

    @Test
    public void testConvertToBasketDto() {
        //Given
        BasketProduct basketProduct = new BasketProduct("PROD1", 1, new BigDecimal(100.00));
        basketProduct.setId(1L);

        List<BasketProduct> basketProductList = new ArrayList<>();
        basketProductList.add(basketProduct);

        Basket basket = new Basket(1L, 1L, true, new BigDecimal(100.00), basketProductList);
        basketProduct.setBasket(basket);

        //When
        BasketDto basketDto = basketMapper.convertToBasketDto(basket);

        //Then
        assertEquals(1L, basketDto.getId().longValue());
        assertEquals(1, basketDto.getBasketProductList().size());
    }
}
