package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.product.ProductDto;
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
public class ProductMapperTestSuite {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testConvertToProductDto() {
        //Given
        Product product = new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));

        //When
        ProductDto productDto = productMapper.convertToProductDto(product);

        //Then
        assertEquals(1L, productDto.getId().longValue());
        assertEquals("Product 1", productDto.getName());
        assertEquals("PROD1", productDto.getIndividualNumber());
    }

    @Test
    public void testConvertToProduct() {
        //Given
        ProductDto productDto = new ProductDto(1L,"Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));

        //When
        Product product = productMapper.convertToProduct(productDto);

        //Then
        assertEquals(1L, product.getId().longValue());
        assertEquals("Product 1", product.getName());
        assertEquals("PROD1", product.getIndividualNumber());
    }

    @Test
    public void testConvertToProductDtoList() {
        //Given
        Product product1 = new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));
        Product product2 = new Product(2L,  "Product 2", "PROD2", "Sample product 2 description", "Product 2 manufacturer", new BigDecimal(2000.00));

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        //When
        List<ProductDto> productDtoList = productMapper.convertProductDtoList(products);

        //Then
        assertEquals(2, products.size());
    }
}
