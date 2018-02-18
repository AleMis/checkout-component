package am.checkoutcomponent.controller;

import am.checkoutcomponent.domain.product.ProductCheckoutDto;
import am.checkoutcomponent.domain.product.ProductDto;
import am.checkoutcomponent.facade.ProductFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/online_shop/products")
public class ProductController {

    @Autowired
    private ProductFacade productFacade;

    @RequestMapping(method = RequestMethod.POST, value = "/create_product")
    public ProductCheckoutDto saveProduct(@RequestBody ProductDto productDto) {
        return productFacade.checkProduct(productDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_product")
    public ProductDto getProduct(@RequestParam Long productId){
        return productFacade.getProductById(productId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_all_products")
    public List<ProductDto> getAllProducts() {
        return productFacade.getProducts();
    }
}
