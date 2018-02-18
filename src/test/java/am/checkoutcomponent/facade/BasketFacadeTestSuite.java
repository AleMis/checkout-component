package am.checkoutcomponent.facade;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.product.BasketProduct;
import am.checkoutcomponent.mapper.BasketMapper;
import am.checkoutcomponent.service.DbService;
import am.checkoutcomponent.validation.BasketValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasketFacadeTestSuite {

    @InjectMocks
    private BasketFacade basketFacade;

    @Mock
    private BasketMapper basketMapper;

    @Mock
    private DbService dbService;

    @Test
    public void testOpenBasketWhenIsClosed() {
        //Given
        Basket closedBasket = new Basket(1L, false);
        List<Basket> basketList = new ArrayList<>();
        basketList.add(closedBasket);

        Basket newBasket = new Basket(1L, true);
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(0.00), new ArrayList<BasketProduct>());

        when(dbService.findAllBasketsByUserId(anyLong())).thenReturn(basketList);
        when(dbService.saveBasket(anyObject())).thenReturn(newBasket);
        when(basketMapper.convertToBasketDto(anyObject())).thenReturn(basketDto);

        //When
        BasketDto createdNewBasket = basketFacade.openBasket(1L);

        //Then
        assertTrue(createdNewBasket.isOpen());
        assertEquals(1L, createdNewBasket.getId().longValue());
        assertEquals(new BigDecimal(0.00), createdNewBasket.getBasketValue());
    }

    @Test
    public void testOpenBasketWhenThereIsNoBasket() {
        //Given
        List<Basket> basketList = new ArrayList<>();

        Basket newBasket = new Basket(1L, true);
        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(0.00), basketProductList);

        when(dbService.findAllBasketsByUserId(anyLong())).thenReturn(basketList);
        when(dbService.saveBasket(anyObject())).thenReturn(newBasket);
        when(basketMapper.convertToBasketDto(anyObject())).thenReturn(basketDto);

        //When
        BasketDto createdNewBasket = basketFacade.openBasket(1L);

        //Then
        assertTrue(createdNewBasket.isOpen());
        assertEquals(1L, createdNewBasket.getId().longValue());
        assertEquals(new BigDecimal(0.00), createdNewBasket.getBasketValue());
    }

    @Test
    public void testCloseUserBasket() {
        //Given
        Basket userBasket = new Basket(1L, true);
        List<Basket> basketList = new ArrayList<>();
        basketList.add(userBasket);

        when(dbService.findAllBasketsByUserId(1L)).thenReturn(basketList);

        userBasket.setOpen(false);

        when(dbService.saveBasket(anyObject())).thenReturn(userBasket);

        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketDto basketDto = new BasketDto(1L, 1L, false, new BigDecimal(0.00), basketProductList);

        when(basketMapper.convertToBasketDto(userBasket)).thenReturn(basketDto);

        //When
        BasketDto closedBasket = basketFacade.closeUserBasket(1L);

        //Then
        assertFalse(closedBasket.isOpen());
        assertEquals(1L, closedBasket.getId().longValue());
    }
}
