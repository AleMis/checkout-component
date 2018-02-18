package am.checkoutcomponent.validation;

import am.checkoutcomponent.domain.warehouse.WarehouseConfirmationDto;
import am.checkoutcomponent.domain.warehouse.Warehouse;
import am.checkoutcomponent.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WarehouseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseValidator.class);

    @Autowired
    private DbService dbService;

    public WarehouseConfirmationDto checkProductAvailability(Long productId, Integer units) {
        Optional<Warehouse> warehouse = dbService.findProductInWarehouseByProductId(productId);
        if(warehouse.isPresent()) {
            return checkWarehouse(warehouse.get(), units);
        }else {
            LOGGER.error("Product [ id = " +  productId + " ] is not available in warehouse");
            return new WarehouseConfirmationDto(false, "Product [ id = " +  productId + " ] is not available in warehouse");
        }
    }

    private WarehouseConfirmationDto checkWarehouse(Warehouse warehouse, Integer units) {
        if(warehouse.getUnits().compareTo(units) >= 0) {
            Integer newUnits = warehouse.getUnits() - units;
            warehouse.setUnits(newUnits);
            dbService.saveWarehouse(warehouse);
            LOGGER.info("Warehouse status for product [ " + warehouse.getProductIndividualNumber() + " ] was updated. Available units in warehouse are: " + warehouse.getUnits());
            return new WarehouseConfirmationDto(true, "Product [ " + warehouse.getProductIndividualNumber() + " ] was added to the basket." );
        }else {
            LOGGER.error("Product [ " + warehouse.getProductIndividualNumber() + " ] can not be added to the basket because in warehouse has " + warehouse.getUnits() + " units vs order units " + units);
            return new WarehouseConfirmationDto(false, "Product [ " + warehouse.getProductIndividualNumber() + " ] was not added to the basket. Units available: " + warehouse.getUnits());
        }
    }
}
