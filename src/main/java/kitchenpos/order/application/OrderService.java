package kitchenpos.order.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.dto.OrderRequest;
import kitchenpos.order.dto.OrderResponse;
import kitchenpos.order.dto.UpdateOrderStatusRequest;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.domain.OrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public OrderService(
            final MenuRepository menuRepository,
            final OrderRepository orderRepository,
            final OrderTableRepository orderTableRepository
    ) {
        this.menuRepository = menuRepository;
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderResponse create(final OrderRequest orderRequest) {
        final List<Menu> menus = findAllMenuById(orderRequest.findAllMenuIds());
        final OrderTable orderTable = findOrderTableById(orderRequest.getOrderTableId());
        final Order savedOrder = orderRepository.save(orderRequest.createOrder(orderTable, menus));

        return OrderResponse.from(savedOrder);
    }

    @Transactional
    public OrderResponse changeOrderStatus(final Long orderId, final UpdateOrderStatusRequest order) {
        final Order savedOrder = findOrderById(orderId);
        savedOrder.setOrderStatus(order.getOrderStatus());

        return OrderResponse.from(orderRepository.save(savedOrder));
    }

    public List<OrderResponse> list() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    private Menu findMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다."));
    }

    private List<Menu> findAllMenuById(List<Long> menuIds) {
        return menuIds.stream()
                .map(this::findMenuById)
                .collect(Collectors.toList());
    }

    private OrderTable findOrderTableById(Long id) {
        return orderTableRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문테이블 입니다."));
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
    }
}
