package kitchenpos.table.domain;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class OrderTables {
    @OneToMany(mappedBy = "tableGroup", cascade = CascadeType.ALL)
    private List<OrderTable> orderTables = new ArrayList<>();

    protected OrderTables() {
    }

    public void addAll(TableGroup tableGroup, List<OrderTable> orderTables) {
        requireNonNull(tableGroup, "tableGroup");
        requireNonNull(orderTables, "orderTables");
        for (OrderTable orderTable : orderTables) {
            add(tableGroup, orderTable);
        }
    }

    private void add(TableGroup tableGroup, OrderTable orderTable) {
        if (!this.orderTables.contains(orderTable)) {
            orderTables.add(orderTable);
        }
        orderTable.bindTo(tableGroup);
    }

    public List<OrderTable> get() {
        return Collections.unmodifiableList(orderTables);
    }

    public void clear() {
        orderTables.forEach(OrderTable::unbind);
        orderTables.clear();
    }
}