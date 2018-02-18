package am.checkoutcomponent.facade;

import am.checkoutcomponent.domain.discount.CreatedDiscountDto;
import am.checkoutcomponent.domain.discount.Discount;
import am.checkoutcomponent.domain.discount.DiscountDto;
import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.mapper.DiscountMapper;
import am.checkoutcomponent.mapper.ProductMapper;
import am.checkoutcomponent.service.DbService;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DiscountFacadeTestSuite {

    @InjectMocks
    private DiscountFacade discountFacade;

    @Mock
    private DiscountMapper discountMapper;

    @Mock
    private DbService dbService;

    @Test
    public void testCreateDiscount() {
        //Given
        Optional<Product> product = Optional.of(new Product(1L, "Product 1", "PROD1", "Sample product description", "Product manufacturer", new BigDecimal(2000.00)));

        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        DiscountDto discountDto = new DiscountDto(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);

        Discount discount = new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);
        when(dbService.getProductByIndividualNumber("PROD1")).thenReturn(product);
        when(discountMapper.convertToDiscount(discountDto)).thenReturn(discount);
        when(dbService.saveDiscount(discount)).thenReturn(discount);
        when(discountMapper.convertToDiscountDto(discount)).thenReturn(discountDto);

        //When
        CreatedDiscountDto createdDiscountDto = discountFacade.createDiscount(discountDto);

        //Then
        assertTrue(createdDiscountDto.isWasDiscountCreated());
        assertEquals(1L, createdDiscountDto.getDiscountItemRequestDto().getId().longValue());
    }

    @Test
    public void testCreateDiscountWithoutProduct() {
        //Given
        Optional<Product> product = Optional.empty();

        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        DiscountDto discountDto = new DiscountDto(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);

        when(dbService.getProductByIndividualNumber("PROD1")).thenReturn(product);

        //When
        CreatedDiscountDto createdDiscountDto = discountFacade.createDiscount(discountDto);

        //Then
        assertFalse(createdDiscountDto.isWasDiscountCreated());
    }

    @Test
    public void testGetAllDiscounts() {
        //Given
        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        Discount discount = new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);

        List<Discount> discountList = new ArrayList<>();
        discountList.add(discount);

        DiscountDto discountDto = new DiscountDto(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);

        List<DiscountDto> discountDtoList = new ArrayList<>();
        discountDtoList.add(discountDto);

        when(dbService.getAllDiscount()).thenReturn(discountList);
        when(discountMapper.convertToDiscountDtoList(discountList)).thenReturn(discountDtoList);

        //When
        List<DiscountDto> createdDiscontsList = discountFacade.getAllProductsDiscounts();

        //Then
        assertEquals(1, createdDiscontsList.size());
    }

    @Test
    public void testGetAllDiscountsWhenEmptyDatabase() {
        //Given & When
        List<DiscountDto> createdDiscontsList = discountFacade.getAllProductsDiscounts();

        //Then
        assertEquals(0, createdDiscontsList.size());
    }

    @Test
    public void testGetDiscountById() {
        //Given
        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        Optional<Discount> discount = Optional.of(new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until));

        DiscountDto discountDto = new DiscountDto(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00),since, until);

        when(dbService.getDiscountById(anyLong())).thenReturn(discount);
        when(discountMapper.convertToDiscountDto(discount.get())).thenReturn(discountDto);

        //When
        DiscountDto downloadedDiscount = discountFacade.getDiscountById(1L);

        //Then
        assertEquals(1L, downloadedDiscount.getId().longValue());
    }

    @Test
    public void testDeleteDiscount() {
        //Given
        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        Optional<Discount> discount = Optional.of(new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until));

        when(dbService.getDiscountById(anyLong())).thenReturn(discount);
        doNothing().when(dbService).deleteDiscount(anyLong());

        //When
        boolean result = discountFacade.deleteDiscount(anyLong());

        //Then
        assertTrue(result);
        verify(dbService, times(1)).deleteDiscount(anyLong());
    }


}
