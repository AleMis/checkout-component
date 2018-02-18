package am.checkoutcomponent.controller;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.BasketResponseDto;
import am.checkoutcomponent.domain.basket.product.BasketProduct;
import am.checkoutcomponent.domain.warehouse.WarehouseConfirmationDto;
import am.checkoutcomponent.facade.BasketFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BasketController.class)
public class BasketControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasketFacade basketFacade;

    @Test
    public void testOpenBasket() throws Exception {
        //Given
        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(0.00), basketProductList);

        when(basketFacade.openBasket(anyLong())).thenReturn(basketDto);

        //When & Then
        mockMvc.perform(get("/v1/online_shop/basket/open_basket?userId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.open", is(true)));
    }

    @Test
    public void testCloseBasket() throws Exception {
        //Given
        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketProduct basketProduct = new BasketProduct("PROD1", 1, new BigDecimal(100.00));
        basketProductList.add(basketProduct);
        BasketDto basketDto = new BasketDto(1L, 1L, false, new BigDecimal(100.00), basketProductList);

        when(basketFacade.closeUserBasket(anyLong())).thenReturn(basketDto);

        //When & Then
        mockMvc.perform(get("/v1/online_shop/basket/close_basket?userId=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.open", is(false)));
    }

    @Test
    public void testAddToBasket() throws Exception {
        //Given
        List<BasketProduct> basketProductList = new ArrayList<>();
        BasketProduct basketProduct = new BasketProduct("PROD1", 1, new BigDecimal(100.00));
        basketProductList.add(basketProduct);
        BasketDto basketDto = new BasketDto(1L, 1L, true, new BigDecimal(100.00), basketProductList);

        WarehouseConfirmationDto warehouseConfirmationDto = new WarehouseConfirmationDto(true, "Product [ " + basketProduct.getProductIndividualNumber() + " ] was added to the basket.");
        BasketResponseDto basketResponseDto = new BasketResponseDto(warehouseConfirmationDto, basketDto);

        when(basketFacade.addNewProductToBasket(anyLong(), anyString(), anyInt())).thenReturn(basketResponseDto);

        //When & Then
        mockMvc.perform(get("/v1/online_shop/basket/add_to_basket?=userId=1&productIndividualNumber=PROD1&units=1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderConfirmation.productAvailable", is(true)))
                .andExpect(jsonPath("$.orderConfirmation.information", is("Product [ PROD1 ] was added to the basket.")))
                .andExpect(jsonPath("$.basketDto.id", is(1)));
    }
}
