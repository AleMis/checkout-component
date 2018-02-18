package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.discount.Discount;
import am.checkoutcomponent.domain.discount.DiscountDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscountMapperTestSuite {

    @Autowired
    private DiscountMapper discountMapper;

    @Test
    public void testConvertToDiscountDto() {
        //Given
        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);
        Discount discount = new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);

        //When
        DiscountDto discountDto = discountMapper.convertToDiscountDto(discount);

        //Then
        assertEquals(1L, discountDto.getId().longValue());
        assertEquals("PROD1", discountDto.getProductIndividualNumber());
    }

    @Test
    public void testConvertToDiscount() {
        //Given
        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);
        DiscountDto discountDto = new DiscountDto(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);

        //When
        Discount discount = discountMapper.convertToDiscount(discountDto);

        //Then
        assertEquals(1L, discount.getId().longValue());
        assertEquals("PROD1", discount.getProductIndividualNumber());
    }

    @Test
    public void testConvertToDiscountDtoList() {
        //Given
        Calendar since = Calendar.getInstance();
        since.set(2018, Calendar.JANUARY,1);
        Calendar until = Calendar.getInstance();
        until.set(2018, Calendar.JANUARY, 31);

        Discount discount = new Discount(1L, "PROD1", "Sample description", 1, new BigDecimal(100.00), since, until);
        Discount discount2 = new Discount(2L, "PROD2", "Sample description 2", 1, new BigDecimal(200.00), since, until);

        List<Discount> discountList = new ArrayList<>();
        discountList.add(discount);
        discountList.add(discount2);

        //When
        List<DiscountDto> discountDtoList = discountMapper.convertToDiscountDtoList(discountList);

        //Then
        assertEquals(2, discountDtoList.size());
    }
}
