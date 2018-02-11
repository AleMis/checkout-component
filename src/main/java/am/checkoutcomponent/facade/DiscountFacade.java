package am.checkoutcomponent.facade;

import am.checkoutcomponent.domain.discount.*;
import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.mapper.DiscountMapper;
import am.checkoutcomponent.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DiscountFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscountFacade.class);

    @Autowired
    private DbService dbService;

    @Autowired
    private DiscountMapper discountMapper;

    public CreatedDiscountDto createDiscount(DiscountDto discountDto) {
        Optional<Product> productFromDatabase = dbService.getProductByIndividualNumber(discountDto.getProductIndividualNumber());
        if(productFromDatabase.isPresent()) {
            Discount discount = saveDiscount(discountMapper.convertToDiscount(discountDto));
            return  new CreatedDiscountDto(true, discountMapper.convertToDiscountDto(discount));
        }else {
            LOGGER.error("Product [ " + discountDto.getProductIndividualNumber() + " ] not found in database. You have to add product first to database!");
            return new CreatedDiscountDto(false, new DiscountDto());
        }
    }

    private Discount saveDiscount(Discount discountToSave) {
        Discount discount = dbService.saveDiscount(discountToSave);
        LOGGER.info("New discount was added to database [ id = " + discount.getId() + " ]");
        return discount;
    }

    public boolean deleteDiscount(Long id) {
        boolean isPresent = dbService.getDiscountById(id).isPresent();
        if(isPresent) {
            dbService.deleteDiscount(id);
            LOGGER.info("Discount with id = " + id +" was removed from the database." );
            return true;
        }else {
            LOGGER.info("Removing discount with id = " + id +" failed, discount not found in database" );
            return false;
        }
    }

    public List<DiscountDto> getAllProductsDiscounts() {
        List<Discount> discountsList = dbService.getAllDiscount();
        List<DiscountDto> discountItemsDtoList = new ArrayList<>();
        if(discountsList.size() > 0) {
            LOGGER.info("Information about all discounts was downloaded from database.");
            discountItemsDtoList = discountMapper.convertToDiscountDtoList(discountsList);
        }else {
            LOGGER.info("System can't return any information about discounts because no discounts were found in database.");
        }
        return discountItemsDtoList;
    }

    public DiscountDto getDiscountById(Long discountId) {
        Optional<Discount> discount = dbService.getDiscountById(discountId);
        if(discount.isPresent()) {
            LOGGER.info("Discount items [ " + discount + " ] was downloaded from database." );
            return discountMapper.convertToDiscountDto(discount.get());
        }else {
            LOGGER.error("Discount items with id = " + discountId + " not found in database!" );
            return  new DiscountDto();
        }
    }
}
