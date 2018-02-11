package am.checkoutcomponent.domain.warehouse;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "warehouse_id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_individual_number")
    private String productIndividualNumber;

    @Column(name = "units")
    private Integer units;
}
