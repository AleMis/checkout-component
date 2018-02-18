package am.checkoutcomponent.validation;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.BasketResponseDto;
import am.checkoutcomponent.domain.warehouse.WarehouseConfirmationDto;
import am.checkoutcomponent.domain.basket.product.BasketProduct;
import am.checkoutcomponent.domain.discount.Discount;
import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.mapper.BasketMapper;
import am.checkoutcomponent.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BasketValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketValidator.class);

    @Autowired
    private DbService dbService;

    @Autowired
    private BasketMapper basketMapper;

    @Autowired
    private WarehouseValidator warehouseValidator;


    public BasketResponseDto validateBasket(Long userId, String productIndividualNumber, Integer units) {
        List<Basket> userBaskets = dbService.findAllBasketsByUserId(userId);
        List<Basket> openBasket = userBaskets.stream().filter(Basket::isOpen).collect(Collectors.toList());
        Basket userOpenBasket = openBasket.get(0);

        Optional<Product> product = dbService.getProductByIndividualNumber(productIndividualNumber);

        WarehouseConfirmationDto warehouseConfirmationDto = warehouseValidator.checkProductAvailability(product.get().getId(), units);

        BasketProduct basketProduct = createBasketProduct(warehouseConfirmationDto, productIndividualNumber, units);

        if(basketProduct.getProductIndividualNumber() != null) {
            if(basketProduct.getProductIndividualNumber().equals(productIndividualNumber)) {
                userOpenBasket.getBasketProductList().add(basketProduct);
                basketProduct.setBasket(userOpenBasket);
                LOGGER.info("New product [ "+ basketProduct.getProductIndividualNumber() + " ] was added to basket.");
            }
        }

        BasketDto basketDto = returnActualBasket(userOpenBasket);
        return new BasketResponseDto(warehouseConfirmationDto, basketDto);
    }


    private BasketDto returnActualBasket(Basket userBasket) {
        BigDecimal currentBasketValue = new BigDecimal(0);
        for(BasketProduct basketProduct : userBasket.getBasketProductList()) {
            currentBasketValue = currentBasketValue.add(basketProduct.getPrice());
        }
        userBasket.setBasketValue(currentBasketValue);
        return basketMapper.convertToBasketDto(dbService.saveBasket(userBasket));
    }

    private BasketProduct createBasketProduct(WarehouseConfirmationDto warehouseConfirmationDto, String productIndividualNumber, Integer units) {
        BasketProduct basketProduct = new BasketProduct();
        if(warehouseConfirmationDto.isProductAvailable()) {
            Optional<Discount> discount  = dbService.getDiscountByProductIndividualNumber(productIndividualNumber);
            Optional<Product> product = dbService.getProductByIndividualNumber(productIndividualNumber);

            basketProduct.setProductIndividualNumber(productIndividualNumber);
            basketProduct.setUnits(units);

            if(discount.isPresent()) {
                BigDecimal priceAfterDiscount = calculateFinalPrice(product.get(), discount.get(), units);
                basketProduct.setPrice(priceAfterDiscount);
                LOGGER.info("Price after discount [ " + priceAfterDiscount + " ] calculated for product [" + basketProduct.getProductIndividualNumber() + " ] added to basket" );
            }else {
                basketProduct.setPrice(product.get().getBasePrice());
                LOGGER.info("Price for product in basket has been set [ " + basketProduct.getPrice().toString() + " ]." );
            }
        }
        return basketProduct;
    }

    private BigDecimal calculateFinalPrice(Product product, Discount discount, Integer units) {
        BigDecimal finalPrice = null;
        int resultOfComperingUnits = compareUnits(discount, units);
        if(resultOfComperingUnits == 0) {
            finalPrice = discount.getSpecialPrice();
        }else if(resultOfComperingUnits > 0) {
            finalPrice = calculatePriceIfOrderIsBiggerThanDiscountUnits(product, discount, units);
        }else {
            finalPrice = calculatePriceIfOrderIsLowerThanDiscountUnits(product,units);
        }
        return finalPrice;
    }

    private BigDecimal calculatePriceIfOrderIsBiggerThanDiscountUnits(Product product, Discount discount, Integer units) {
        BigDecimal finalPrice = new BigDecimal(0);
        Integer temporarUnits = units;
        Integer discountUnits = discount.getUnits();
        while (temporarUnits != 0) {
            finalPrice.add(discount.getSpecialPrice());
            temporarUnits = temporarUnits - discountUnits;
            if(temporarUnits.compareTo(discountUnits) < 0) {
                finalPrice.add(product.getBasePrice().multiply(new BigDecimal(temporarUnits)));
            }
        }
        return finalPrice;
    }

    private BigDecimal calculatePriceIfOrderIsLowerThanDiscountUnits(Product product, Integer units) {
        return product.getBasePrice().multiply(new BigDecimal(units));
    }

    private int compareUnits(Discount discount, Integer units) {
        return units.compareTo(discount.getUnits());
    }
}
