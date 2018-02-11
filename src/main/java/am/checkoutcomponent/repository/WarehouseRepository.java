package am.checkoutcomponent.repository;

import am.checkoutcomponent.domain.warehouse.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

    Optional<Warehouse> findWarehouseByProductId(Long id);

    @Override
    Warehouse save(Warehouse warehouse);
}
