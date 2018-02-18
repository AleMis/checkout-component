package am.checkoutcomponent.controller;

import am.checkoutcomponent.domain.product.ProductCheckoutDto;
import am.checkoutcomponent.domain.product.ProductDto;
import am.checkoutcomponent.facade.ProductFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductFacade productFacade;

    @Test
    public void testSaveProduct() throws Exception {
        //Given
        ProductDto productDto = new ProductDto(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));
        ProductCheckoutDto productCheckoutDto = new ProductCheckoutDto(true, productDto);

        when(productFacade.checkProduct(anyObject())).thenReturn(productCheckoutDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDto);

        //When & Then
        mockMvc.perform(post("/v1/online_shop/products/create_product")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.created", is(true)))
                .andExpect(jsonPath("$.productDto.name", is("Product 1")));
    }

    @Test
    public void testGetProduct() throws Exception {
        ProductDto productDto = new ProductDto(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));

        Long productId = 1L;
        when(productFacade.getProductById(productId)).thenReturn(productDto);

        //When & Then
        mockMvc.perform(get("/v1/online_shop/products/get_product?productId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 1")));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        //Given
        ProductDto productDto = new ProductDto(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto);

        when(productFacade.getProducts()).thenReturn(productDtoList);

        //When & Then
        mockMvc.perform(get("/v1/online_shop/products/get_all_products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Product 1")));
    }
}
