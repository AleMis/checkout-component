package am.checkoutcomponent.domain.basket.product;

import am.checkoutcomponent.domain.basket.Basket;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "basket_products")
public class BasketProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "basket_product_id")
    private Long id;

    @Column(name = "product_individual_number")
    private String productIndividualNumber;

    @Column(name = "units")
    private Integer units;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    @JsonIgnore
    private Basket basket;
}
