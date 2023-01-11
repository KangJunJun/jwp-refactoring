package kitchenpos.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import kitchenpos.ordertable.domain.OrderTable;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderTable orderTable;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime orderedTime;

    @Embedded
    private OrderLineItems orderLineItems = new OrderLineItems();

    protected Order(){
    }

    public Order(
            OrderTable orderTable,
            OrderStatus orderStatus,
            LocalDateTime orderedTime
    ) {
        validate(orderTable);
        this.orderTable = orderTable;
        this.orderStatus = orderStatus;
        this.orderedTime = orderedTime;
    }

    private void validate(OrderTable orderTable) {
        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException("OrderTable 이 비어있습니다.");
        }
    }


    public OrderTable getOrderTable() {
        return orderTable;
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems.get();
    }


    public void setOrderLineItems(OrderLineItems orderLineItems) {
        this.orderLineItems = orderLineItems;
        orderLineItems.setOrder(this);
    }

    public void validateOrderStatusShouldComplete() {
        if (!OrderStatus.COMPLETION.equals(orderStatus)) {
            throw new IllegalArgumentException("계산이 완료된 주문이 아닙니다.");
        }
    }
}
