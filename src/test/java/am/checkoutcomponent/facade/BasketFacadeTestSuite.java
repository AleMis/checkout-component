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
        Optional<Basket> closedBasket = Optional.of(new Basket(1L, false));

        Basket newBasket = new Basket(1L, true);
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(0.00), new ArrayList<BasketProduct>());

        when(dbService.findBasketByUserId(anyLong())).thenReturn(closedBasket);
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
        Optional<Basket> closedBasket = Optional.empty();

        Basket newBasket = new Basket(1L, true);
        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(0.00), basketProductList);

        when(dbService.findBasketByUserId(anyLong())).thenReturn(closedBasket);
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
        Optional<Basket> userBasket = Optional.of(new Basket(1L, false));
        Basket closedUserBasket = userBasket.get();
        closedUserBasket.setOpen(false);
        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(0.00), basketProductList);

        when(dbService.findBasketByUserId(anyLong())).thenReturn(userBasket);
        when(dbService.saveBasket(closedUserBasket)).thenReturn(closedUserBasket);
        when(basketMapper.convertToBasketDto(closedUserBasket)).thenReturn(basketDto);

        //When
        BasketDto closedBasket = basketFacade.closeUserBasket(anyLong());

        //Then
        assertTrue(closedBasket.isOpen());
        assertEquals(1L, closedBasket.getId().longValue());
    }
}
