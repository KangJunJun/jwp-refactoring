package kitchenpos.tablegroup.dto;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.ordertable.dto.OrderTableResponse;

public class TableGroupRequest {
    private List<OrderTableResponse> orderTables;

    public TableGroupRequest(List<OrderTableResponse> orderTables) {
        this.orderTables = orderTables;
    }

    public TableGroupRequest() {
    }

    public List<Long> toOrderTableId() {
        return orderTables.stream()
                .map(OrderTableResponse::getId)
                .collect(Collectors.toList());
    }

    public List<OrderTableResponse> getOrderTables() {
        return orderTables;
    }
}