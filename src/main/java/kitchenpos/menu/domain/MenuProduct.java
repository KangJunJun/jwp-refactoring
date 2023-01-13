package kitchenpos.menu.domain;

import java.math.BigDecimal;
import javax.persistence.*;
import kitchenpos.product.domain.Product;


@Entity
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private Long quantity;

    protected MenuProduct() {
    }



    public MenuProduct(Product product, long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void bindTo(Menu menu) {
        this.menu = menu;
    }


    public Long getSeq() {
        return seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public Product getProduct() {
        return product;
    }
    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal calculateAmount() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
