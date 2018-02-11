package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.product.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto mapProductToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getIndividualNumber(), product.getDescription(), product.getManufacturer(), product.getBasePrice());
    }

    public Product mapProductDtoToProduct(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getIndividualNumber(), productDto.getDescription(), productDto.getManufacturer(), productDto.getBasePrice());
    }
}
