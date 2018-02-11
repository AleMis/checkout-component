package am.checkoutcomponent.controller;

import am.checkoutcomponent.domain.discount.*;
import am.checkoutcomponent.facade.DiscountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v1/online_shop/discounts")
public class DiscountController {

    @Autowired
    private DiscountFacade discountFacade;

    @RequestMapping(method = RequestMethod.POST, value = "/create_discount")
    public CreatedDiscountDto createDiscount(@RequestBody DiscountDto discountDto) {
        return discountFacade.createDiscount(discountDto);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete_discount")
    public boolean deleteDiscount(@RequestParam Long discountId) {
        return discountFacade.deleteDiscount(discountId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_all_discounts")
    public List<DiscountDto> getAllDiscountItems() {
        return discountFacade.getAllProductsDiscounts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_discount")
    public DiscountDto getDiscountItems(@RequestParam Long discountId) {
        return discountFacade.getDiscountById(discountId);
    }
}
