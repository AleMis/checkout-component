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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Basket userBasket = filtrBaskets(userId);
        if (userBasket != null) {
            return checkIfBasketIsOpen(userBasket, userId);
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
        Basket userBasket = filtrBaskets(userId);
        userBasket.setOpen(false);
        LOGGER.info("Basket of user with id = " +  userId + " was closed!");
        return basketMapper.convertToBasketDto(dbService.saveBasket(userBasket));
    }

    public BasketResponseDto addNewProductToBasket(Long userId, String productIndividualNumber, Integer units) {
        return basketValidator.validateBasket(userId, productIndividualNumber, units);
    }

    private Basket filtrBaskets(Long userId) {
        List<Basket> userBaskets = dbService.findAllBasketsByUserId(userId);
        Basket basket = null;
        if(userBaskets.size() == 0) {
            return new Basket();
        }else {
            List<Basket> filteredUserBasket = userBaskets.stream().filter(Basket::isOpen).collect(Collectors.toList());
            if(filteredUserBasket.size() == 1) {
                basket =  filteredUserBasket.get(0);
            }else {
                return new Basket();
            }
        }
        return basket;
    }
}
