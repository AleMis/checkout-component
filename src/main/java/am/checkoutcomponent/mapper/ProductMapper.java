package am.checkoutcomponent.mapper;

import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.product.ProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductDto convertToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getIndividualNumber(), product.getDescription(), product.getManufacturer(), product.getBasePrice());
    }

    public Product convertToProduct(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getIndividualNumber(), productDto.getDescription(), productDto.getManufacturer(), productDto.getBasePrice());
    }

    public List<ProductDto> convertProductDtoList(List<Product> productList) {
        return productList.stream().map(product -> new ProductDto(product.getId(), product.getName(), product.getIndividualNumber(), product.getDescription(), product.getManufacturer(), product.getBasePrice())).collect(Collectors.toList());
    }
}
