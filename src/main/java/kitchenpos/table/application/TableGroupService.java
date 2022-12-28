package kitchenpos.table.application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.dao.OrderDao;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.table.domain.OrderTable;
import kitchenpos.table.domain.OrderTableRepository;
import kitchenpos.table.domain.TableGroup;
import kitchenpos.table.domain.TableGroupRepository;
import kitchenpos.table.dto.TableGroupRequest;
import kitchenpos.table.dto.TableGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class TableGroupService {
    private final OrderDao orderDao;
    private final OrderTableRepository orderTableRepository;
    private final TableGroupRepository tableGroupRepository;

    public TableGroupService(final OrderDao orderDao, final OrderTableRepository orderTableRepository, TableGroupRepository tableGroupRepository) {
        this.orderDao = orderDao;
        this.orderTableRepository = orderTableRepository;
        this.tableGroupRepository = tableGroupRepository;
    }

    @Transactional
    public TableGroupResponse create(final TableGroupRequest tableGroup) {
        List<OrderTable> orderTables = validateCreate(tableGroup);
        TableGroup savedTableGroup = tableGroupRepository.save(new TableGroup());
        savedTableGroup.addOrderTables(orderTables);
        return TableGroupResponse.of(savedTableGroup);
    }


    private List<OrderTable> validateCreate(TableGroupRequest request) {
        List<Long> orderTableIds = validateNotEmptyIds(request);
        return validateExistsAllOrderTables(orderTableIds);
    }


    private List<OrderTable> validateExistsAllOrderTables(List<Long> orderTableIds) {
        List<OrderTable> orderTables = orderTableRepository.findAllByIdIn(orderTableIds);
        if (orderTableIds.size() != orderTables.size()) {
            throw new IllegalArgumentException("존재하지 않는 테이블이 있습니다.");
        }
        return orderTables;
    }

    private List<Long> validateNotEmptyIds(TableGroupRequest request) {
        List<Long> orderTableIds = request.toOrderTableId();
        if (CollectionUtils.isEmpty(orderTableIds)) {
            throw new IllegalArgumentException("단체 지정할 테이블이 없습니다.");
        }
        return orderTableIds;
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        final List<OrderTable> orderTables = orderTableRepository.findAllByTableGroupId(tableGroupId);

        final List<Long> orderTableIds = orderTables.stream()
                .map(OrderTable::getId)
                .collect(Collectors.toList());

        if (orderDao.existsByOrderTableIdInAndOrderStatusIn(
                orderTableIds, Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name()))) {
            throw new IllegalArgumentException();
        }

        for (final OrderTable orderTable : orderTables) {
            orderTable.unbind();
            orderTableRepository.save(orderTable);
        }
    }
}
