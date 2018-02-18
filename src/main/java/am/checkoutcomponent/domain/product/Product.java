package am.checkoutcomponent.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "individual_number")
    private String individualNumber;

    @Column(name = "description")
    private String description;

    @Column(name= "manufacturer")
    private String manufacturer;

    @Column(name = "base_price")
    private BigDecimal basePrice;


}
