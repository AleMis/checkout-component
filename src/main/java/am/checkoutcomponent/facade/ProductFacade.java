package am.checkoutcomponent.facade;

import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.product.ProductCheckoutDto;
import am.checkoutcomponent.domain.product.ProductDto;
import am.checkoutcomponent.mapper.ProductMapper;
import am.checkoutcomponent.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class ProductFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFacade.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private DbService dbService;

    public ProductCheckoutDto checkProduct(ProductDto productDto) {
        Optional<Product> productFromDatabase = dbService.getProductByIndividualNumber(productDto.getIndividualNumber());
        if(productFromDatabase.isPresent()) {
            LOGGER.info("Product [ "  + productDto.getIndividualNumber() + " ] is already stored in the database.");
            return new ProductCheckoutDto(false, productMapper.mapProductToProductDto(productFromDatabase.get()));
        }else {
            LOGGER.info("Product [ " + productDto.getIndividualNumber() + " ] was added to database.");
            return new ProductCheckoutDto(true, productMapper.mapProductToProductDto(dbService.saveItem(productMapper.mapProductDtoToProduct(productDto))));
        }
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> productFromDatabase = dbService.getProductById(productId);
        if(productFromDatabase.isPresent()) {
            LOGGER.info("Product with id = "  + productId + " was downloaded from database.");
            return productMapper.mapProductToProductDto(productFromDatabase.get());
        }else {
            LOGGER.error("Product with id =  " + productId + " not find in database.");
            return new ProductDto();
        }
    }

    public List<ProductDto> getProducts() {
        List<Product> allProducts = dbService.getAllProducts();
        if(allProducts.size() > 0) {
            LOGGER.info("Products was downloaded from database.");
            return allProducts.stream().map(product -> new ProductDto(product.getId(), product.getName(), product.getIndividualNumber(), product.getDescription(), product.getManufacturer(), product.getBasePrice())).collect(Collectors.toList());
        }else {
            LOGGER.error("No product was found in database.");
            return new ArrayList<>();
        }
    }
}
