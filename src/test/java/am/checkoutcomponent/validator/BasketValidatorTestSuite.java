package am.checkoutcomponent.validator;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.BasketResponseDto;
import am.checkoutcomponent.domain.basket.product.BasketProduct;
import am.checkoutcomponent.domain.discount.Discount;
import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.warehouse.WarehouseConfirmationDto;
import am.checkoutcomponent.mapper.BasketMapper;
import am.checkoutcomponent.service.DbService;
import am.checkoutcomponent.validation.BasketValidator;
import am.checkoutcomponent.validation.WarehouseValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketValidatorTestSuite {

    @InjectMocks
    private BasketValidator basketValidator;

    @Mock
    private BasketMapper basketMapper;

    @Mock
    private DbService dbService;

    @Mock
    private WarehouseValidator warehouseValidator;

    @Test
    public void testValidateBasket() {
        //Given
        List<BasketProduct> basketProductList = new ArrayList<>();
        Optional<Basket> userBasket = Optional.of(new Basket(1L, true));
        userBasket.get().setId(1L);
        userBasket.get().setBasketValue(new BigDecimal(0.00));
        userBasket.get().setBasketProductList(basketProductList);

        List<Basket> basketsList = new ArrayList<>();
        basketsList.add(userBasket.get());

        Optional<Product> product = Optional.of(new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(100.00)));

        WarehouseConfirmationDto warehouseConfirmationDto = new WarehouseConfirmationDto(true, "Product [ " + product.get().getIndividualNumber() + " ] was added to the basket.");

        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        Optional<Discount> discount = Optional.of(new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until));

        when(dbService.findAllBasketsByUserId(anyLong())).thenReturn(basketsList);
        when(dbService.getProductByIndividualNumber(anyString())).thenReturn(product);
        when(warehouseValidator.checkProductAvailability(anyLong(), anyInt())).thenReturn(warehouseConfirmationDto);
        when(dbService.getDiscountByProductIndividualNumber(anyString())).thenReturn(discount);
        when(dbService.getProductByIndividualNumber(anyString())).thenReturn(product);

        BasketProduct basketProduct = new BasketProduct("PROD1", 1, new BigDecimal(100.00));
        basketProduct.setId(1L);
        basketProduct.setBasket(userBasket.get());

        basketProductList.add(basketProduct);
        Basket updatedBasket = new Basket(1L, 1L, true, new BigDecimal(100.00), basketProductList);
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(100.00), basketProductList);

        when(dbService.saveBasket(anyObject())).thenReturn(updatedBasket);
        when(basketMapper.convertToBasketDto(anyObject())).thenReturn(basketDto);

        //When
        BasketResponseDto basketResponseDto = basketValidator.validateBasket(1L, "PROD1", 1);

        //Then
        assertTrue(basketResponseDto.getBasketDto().isOpen());
        assertEquals(new BigDecimal(100.00), basketResponseDto.getBasketDto().getBasketValue());
        assertTrue(basketResponseDto.getOrderConfirmation().isProductAvailable());
    }
}
