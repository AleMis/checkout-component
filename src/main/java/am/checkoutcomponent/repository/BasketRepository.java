package am.checkoutcomponent.repository;

import am.checkoutcomponent.domain.basket.Basket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BasketRepository extends CrudRepository<Basket, Long>{

    @Override
    Basket save(Basket basket);

    Optional<Basket> findByUserId(Long id);

    List<Basket> findAllByUserId(Long id);

}
