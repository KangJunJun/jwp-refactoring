package kitchenpos.order.domain;

import javax.persistence.*;
import kitchenpos.menu.domain.Menu;

@Entity
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(nullable = false)
    private long quantity;

    public OrderLineItem() {}

    public OrderLineItem(Long seq, Long quantity, Menu menu) {
        this.seq = seq;
        this.quantity = quantity;
        this.menu = menu;
    }

    public OrderLineItem(Long quantity, Menu menu) {
        this.quantity = quantity;
        this.menu = menu;
    }

    public Long getSeq() {
        return seq;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Menu getMenu() {
        return menu;
    }

    public Long getMenuId() {
        return menu.getId();
    }

    public long getQuantity() {
        return quantity;
    }

    public void updateOrder(Order order){
        this.order = order;
    }
}
