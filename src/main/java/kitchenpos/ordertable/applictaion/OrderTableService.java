package kitchenpos.ordertable.applictaion;

import java.util.Arrays;
import java.util.List;
import kitchenpos.dao.OrderDao;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.domain.OrderTableRepository;
import kitchenpos.ordertable.dto.OrderTableRequest;
import kitchenpos.ordertable.dto.OrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderTableService {
    private final OrderDao orderDao;
    private final OrderTableRepository orderTableRepository;

    public OrderTableService(final OrderDao orderDao, final OrderTableRepository orderTableRepository) {
        this.orderDao = orderDao;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderTableResponse create(final OrderTableRequest orderTable) {
        return OrderTableResponse.of(orderTableRepository.save(orderTable.toOrderTable()));
    }

    public List<OrderTableResponse> list() {
        return OrderTableResponse.of(orderTableRepository.findAll());
    }

    @Transactional
    public OrderTableResponse changeEmpty(final Long orderTableId, final OrderTable orderTable) {
        final OrderTable savedOrderTable = getSavedOrderTable(orderTableId);
        if (orderDao.existsByOrderTableIdAndOrderStatusIn(
                orderTableId, Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name()))) {
            throw new IllegalArgumentException();
        }

        savedOrderTable.changeEmpty(orderTable.isEmpty());

        return OrderTableResponse.of(orderTableRepository.save(savedOrderTable));
    }
    @Transactional
    public OrderTableResponse changeNumberOfGuests(final Long orderTableId, final OrderTable orderTable) {
        final OrderTable savedOrderTable = getSavedOrderTable(orderTableId);
        savedOrderTable.changeNumberOfGuests(orderTable.getNumberOfGuests());

        return OrderTableResponse.of(orderTableRepository.save(savedOrderTable));
    }


    private OrderTable getSavedOrderTable(Long orderTableId) {
        return orderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("주문테이블이 존재하지 않습니다."));
    }

}
