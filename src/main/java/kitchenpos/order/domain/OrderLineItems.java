package kitchenpos.order.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.springframework.util.CollectionUtils;

@Embeddable
public class OrderLineItems {
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();

    protected OrderLineItems() {}


    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        validate(orderLineItems);
        this.orderLineItems = new ArrayList<>(orderLineItems);
    }
    private void validate(List<OrderLineItem> orderLineItems) {
        if (CollectionUtils.isEmpty(orderLineItems)) {
            throw new IllegalArgumentException("주문항목이 비어있습니다.");
        }
    }

    public void setOrder(final Order order) {
        orderLineItems.forEach(orderLineItem -> orderLineItem.setOrder(order));
    }

    public List<OrderLineItem> get() {
        return Collections.unmodifiableList(orderLineItems);
    }
}
