package am.checkoutcomponent.facade;

import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.BasketResponseDto;
import am.checkoutcomponent.mapper.BasketMapper;
import am.checkoutcomponent.service.DbService;
import am.checkoutcomponent.validation.BasketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasketFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasketFacade.class);

    @Autowired
    private DbService dbService;

    @Autowired
    private BasketMapper basketMapper;

    @Autowired
    private BasketValidator basketValidator;

    public BasketDto openBasket(Long userId) {
        Optional<Basket> userBasket = dbService.findBasketByUserId(userId);
        if (userBasket.isPresent()) {
            return checkIfBasketIsOpen(userBasket.get(), userId);
        } else {
            LOGGER.info("Basket of user with id = " + userId + " is not available in database.");
            return createNewBasket(userId);
        }
    }

    private BasketDto checkIfBasketIsOpen(Basket userBasket, Long userId) {
        if(userBasket.isOpen()) {
            LOGGER.info("Basket of user with id = " + userId + " was download from database [basket id = " + userBasket.getId() + " ]");
            return basketMapper.convertToBasketDto(userBasket);
        }else {
            LOGGER.info("Basket of user with id = " + userId + " is closed.");
            return createNewBasket(userId);
        }
    }

    private BasketDto createNewBasket(Long userId) {
        Basket basket = new Basket(userId, true);
        dbService.saveBasket(basket);
        LOGGER.info("New basket for user with id = " + userId + " was created!");
        return basketMapper.convertToBasketDto(basket);
    }

    public BasketDto closeUserBasket(Long userId) {
        Optional<Basket> userBasket = dbService.findBasketByUserId(userId);
        userBasket.get().setOpen(false);
        LOGGER.info("Basket of user with id = " +  userId + " was closed!");
        return basketMapper.convertToBasketDto(dbService.saveBasket(userBasket.get()));
    }

    public BasketResponseDto addNewProductToBasket(Long userId, String productIndividualNumber, Integer units) {
        return basketValidator.validateBasket(userId, productIndividualNumber, units);
    }
}
