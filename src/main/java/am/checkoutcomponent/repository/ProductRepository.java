package am.checkoutcomponent.repository;

import am.checkoutcomponent.domain.product.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ProductRepository extends CrudRepository<Product, Long>{

    @Override
    List<Product> findAll();

    @Override
    Product save(Product item);

    Optional<Product> findItemById(Long id);

    Optional<Product> findItemByIndividualNumber(String individualNumber);

}
