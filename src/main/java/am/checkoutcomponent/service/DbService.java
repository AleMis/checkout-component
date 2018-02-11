package am.checkoutcomponent.service;


import am.checkoutcomponent.domain.basket.Basket;
import am.checkoutcomponent.domain.discount.Discount;
import am.checkoutcomponent.domain.product.Product;
import am.checkoutcomponent.domain.warehouse.Warehouse;
import am.checkoutcomponent.repository.BasketRepository;
import am.checkoutcomponent.repository.DiscountRepository;
import am.checkoutcomponent.repository.ProductRepository;
import am.checkoutcomponent.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DbService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Product saveItem(Product item) {
        return productRepository.save(item);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findItemById(id);
    }

    public Optional<Product> getProductByIndividualNumber(String individualNumber) {
        return productRepository.findItemByIndividualNumber(individualNumber);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Discount saveDiscount(Discount discountItems) {
        return discountRepository.save(discountItems);
    }

    public Optional<Discount> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }

    public Optional<Discount> getDiscountByProductIndividualNumber(String productIndividualNumber) {
        return discountRepository.findByProductIndividualNumber(productIndividualNumber);
    }

    public void deleteDiscount(Long id) {
        discountRepository.delete(id);
    }

    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    public Basket saveBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public Optional<Basket> findBasketByUserId(Long userId) {
        return basketRepository.findByUserId(userId);
    }

    public Optional<Warehouse> findProductInWarehouseByProductId(Long productId) {
        return warehouseRepository.findWarehouseByProductId(productId);
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Basket> findAllBasketsByUserId(Long userId) {
       return basketRepository.findAllByUserId(userId);
    }
}
