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
            ProductDto productFromDatabaseDto = productMapper.convertToProductDto(productFromDatabase.get());
            return new ProductCheckoutDto(false, productFromDatabaseDto);
        }else {
            LOGGER.info("Product [ " + productDto.getIndividualNumber() + " ] was added to database.");
            ProductDto productFromDatabaseDto = productMapper.convertToProductDto(dbService.saveItem(productMapper.convertToProduct(productDto)));
            return new ProductCheckoutDto(true, productFromDatabaseDto);
        }
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> productFromDatabase = dbService.getProductById(productId);
        if(productFromDatabase.isPresent()) {
            LOGGER.info("Product with id = "  + productId + " was downloaded from database.");
            return productMapper.convertToProductDto(productFromDatabase.get());
        }else {
            LOGGER.error("Product with id =  " + productId + " not find in database.");
            return new ProductDto();
        }
    }

    public List<ProductDto> getProducts() {
        List<Product> allProducts = dbService.getAllProducts();
        if(allProducts.size() > 0) {
            LOGGER.info("Products was downloaded from database.");
            return productMapper.convertProductDtoList(allProducts);
        }else {
            LOGGER.error("No product was found in database.");
            return new ArrayList<>();
        }
    }
}
