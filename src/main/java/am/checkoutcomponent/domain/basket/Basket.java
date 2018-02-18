package am.checkoutcomponent.domain.basket;

import am.checkoutcomponent.domain.basket.product.BasketProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "baskets")
public class Basket {

    public Basket(Long userId, boolean isOpen) {
        this.userId = userId;
        this.isOpen = isOpen;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "basket_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "basket_value")
    private BigDecimal basketValue;

    @OneToMany(targetEntity = BasketProduct.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "basket")
    private List<BasketProduct> basketProductList;

}
