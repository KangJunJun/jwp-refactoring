package kitchenpos.tablegroup.application;

import java.util.List;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.domain.OrderTableRepository;
import kitchenpos.tablegroup.domain.TableGroup;
import kitchenpos.tablegroup.domain.TableGroupRepository;
import kitchenpos.tablegroup.dto.TableGroupRequest;
import kitchenpos.tablegroup.dto.TableGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TableGroupService {
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(final OrderTableRepository orderTableRepository,
                             final TableGroupRepository tableGroupRepository, final OrderRepository orderRepository) {
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public TableGroupResponse create(final TableGroupRequest tableGroup) {
        List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(tableGroup.toOrderTableId());
        TableGroup savedTableGroup = tableGroupRepository.save(new TableGroup());
        savedTableGroup.addOrderTables(orderTables);
        return TableGroupResponse.of(savedTableGroup);
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        TableGroup tableGroup = findTableGroupById(tableGroupId);
        List<Order> orders = orderRepository.findAllByOrderTableIdIn(tableGroup.getOrderTableIds());
        tableGroup.ungroup(orders);
        tableGroupRepository.save(tableGroup);
    }

    private TableGroup findTableGroupById(Long id) {
        return tableGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 테이블 그룹이 존재하지 않습니다."));
    }
}
