package am.checkoutcomponent.validator;

import am.checkoutcomponent.domain.warehouse.Warehouse;
import am.checkoutcomponent.domain.warehouse.WarehouseConfirmationDto;
import am.checkoutcomponent.service.DbService;
import am.checkoutcomponent.validation.WarehouseValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WarehouseValidatorTestSuite {

    @InjectMocks
    private WarehouseValidator warehouseValidator;

    @Mock
    private DbService dbService;

    @Test
    public void testCheckProductAvailabilityIfNotAvailable() {
        //Given
        Optional<Warehouse> warehouse = Optional.empty();

        when(dbService.findProductInWarehouseByProductId(anyLong())).thenReturn(warehouse);

        //When
        WarehouseConfirmationDto warehouseConfirmationDto = warehouseValidator.checkProductAvailability(1L, 5);

        //Then
        assertFalse(warehouseConfirmationDto.isProductAvailable());
        assertEquals("Product [ id = 1 ] is not available in warehouse", warehouseConfirmationDto.getInformation());
    }

    @Test
    public void testCheckProductAvailabilityIfUnitsAboveZero() {
        //Given
        Optional<Warehouse> warehouse = Optional.of(new Warehouse(1L, 1L, "PROD1", 10));
        Warehouse warehouseAfterModification = new Warehouse(1L, 1L, "PROD1", 5);

        when(dbService.findProductInWarehouseByProductId(anyLong())).thenReturn(warehouse);
        when(dbService.saveWarehouse(anyObject())).thenReturn(warehouseAfterModification);

        //When
        WarehouseConfirmationDto warehouseConfirmationDto = warehouseValidator.checkProductAvailability(1L, 5);

        //Then
        assertTrue(warehouseConfirmationDto.isProductAvailable());
        assertEquals("Product [ PROD1 ] was added to the basket.", warehouseConfirmationDto.getInformation());
    }

    @Test
    public void testCheckProductAvailabilityIfUnitsEqualZero() {
        //Given
        Optional<Warehouse> warehouse = Optional.of(new Warehouse(1L, 1L, "PROD1", 0));

        when(dbService.findProductInWarehouseByProductId(anyLong())).thenReturn(warehouse);

        //When
        WarehouseConfirmationDto warehouseConfirmationDto = warehouseValidator.checkProductAvailability(1L, 5);

        //Then
        assertFalse(warehouseConfirmationDto.isProductAvailable());
        assertEquals("Product [ PROD1 ] was not added to the basket. Units available: 0", warehouseConfirmationDto.getInformation());
    }
}
