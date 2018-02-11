package am.checkoutcomponent.controller;


import am.checkoutcomponent.domain.basket.BasketDto;
import am.checkoutcomponent.domain.basket.BasketResponseDto;
import am.checkoutcomponent.facade.BasketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/online_shop/basket")
public class BasketController {

    @Autowired
    private BasketFacade basketFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/open_basket")
    private BasketDto opentBasket(@RequestParam Long userId) {
        return basketFacade.openBasket(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/close_basket")
    private BasketDto closeBasket(@RequestParam Long userId) {
        return basketFacade.closeUserBasket(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add_to_basket")
    private BasketResponseDto addToBasket(@RequestParam Long userId, @RequestParam String productIndividualNumber, @RequestParam Integer units) {
        return basketFacade.addNewProductToBasket(userId, productIndividualNumber, units);
    }
}
