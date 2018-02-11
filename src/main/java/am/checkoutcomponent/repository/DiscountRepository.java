package am.checkoutcomponent.repository;

import am.checkoutcomponent.domain.discount.Discount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {

    @Override
    Discount save(Discount discountItemRequest);

    Optional<Discount> findById(Long id);

    Optional<Discount> findByProductIndividualNumber(String productIndividualNumber);

    void delete(Long id);

    List<Discount> findAll();
}
