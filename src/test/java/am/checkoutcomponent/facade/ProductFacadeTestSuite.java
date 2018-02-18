package am.checkoutcomponent.facade;

import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.product.ProductCheckoutDto;
import am.checkoutcomponent.domain.product.ProductDto;
import am.checkoutcomponent.mapper.ProductMapper;
import am.checkoutcomponent.service.DbService;

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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductFacadeTestSuite {

    @InjectMocks
    private ProductFacade productFacade;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private DbService dbService;

    @Test
    public void testCheckProductAddProductIfPresent() {
        //Given
        Optional<Product> product = Optional.of(new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00)));
        ProductDto productDto = new ProductDto(1L,"Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));

        ProductCheckoutDto productCheckoutDto = new ProductCheckoutDto(false, productDto);

        //When
        when(dbService.getProductByIndividualNumber("PROD1")).thenReturn(product);
        when(productMapper.convertToProductDto(product.get())).thenReturn(productDto);

        ProductCheckoutDto createdProductCheckoutDto = productFacade.checkProduct(productDto);

        //Then
        assertEquals(false, createdProductCheckoutDto.isCreated());
        assertEquals(1L, createdProductCheckoutDto.getProductDto().getId().longValue());
        assertEquals("Product 1", createdProductCheckoutDto.getProductDto().getName());
    }

    @Test
    public void testCheckProductAddProductIfNotPresent() {
        //Given
        Optional<Product> emptyProduct = Optional.empty();
        Product product = new Product(1L,"Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));
        ProductDto productDto = new ProductDto(1L,"Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));

        ProductCheckoutDto productCheckoutDto = new ProductCheckoutDto(true, productDto);

        when(dbService.getProductByIndividualNumber(anyString())).thenReturn(emptyProduct);
        when(productMapper.convertToProduct(productDto)).thenReturn(product);
        when(dbService.saveItem(product)).thenReturn(product);
        when(productMapper.convertToProductDto(product)).thenReturn(productDto);

        //When
        ProductCheckoutDto createdProductCheckoutDto = productFacade.checkProduct(productDto);

        //Then
        assertEquals(true, createdProductCheckoutDto.isCreated());
        assertEquals(1L, createdProductCheckoutDto.getProductDto().getId().longValue());
        assertEquals("Product 1", createdProductCheckoutDto.getProductDto().getName());
    }

    @Test
    public void testGetProducts() {
        //Given
        Product product1 = new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));
        Product product2 = new Product(2L,  "Product 2", "PROD2", "Sample product 2 description", "Product 2 manufacturer", new BigDecimal(2000.00));

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        ProductDto productDto1 = new ProductDto(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));
        ProductDto productDto2 = new ProductDto(2L,  "Product 2", "PROD2", "Sample product 2 description", "Product 2 manufacturer", new BigDecimal(2000.00));

        List<ProductDto> productsDto = new ArrayList<>();
        productsDto.add(productDto1);
        productsDto.add(productDto2);

        when(dbService.getAllProducts()).thenReturn(products);
        when(productMapper.convertProductDtoList(products)).thenReturn(productsDto);

        //When
        List<ProductDto> productDtoList = productFacade.getProducts();

        //Then
        assertEquals(2, productDtoList.size());
    }

    @Test
    public void testGetProductByIdIfPresent() {
        //Given
        Optional<Product> product = Optional.of(new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00)));
        ProductDto productDto = new ProductDto(1L,"Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00));

        when(dbService.getProductById(1L)).thenReturn(product);
        when(productMapper.convertToProductDto(product.get())).thenReturn(productDto);

        //When
        ProductDto productToReturn = productFacade.getProductById(1L);

        //Then
        assertEquals(1L, productToReturn.getId().longValue());
        assertEquals("Product 1", productToReturn.getName());
        assertEquals("PROD1", productToReturn.getIndividualNumber());
    }
}
