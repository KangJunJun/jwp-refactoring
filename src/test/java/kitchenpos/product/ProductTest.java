package kitchenpos.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.menu.product.domain.Product;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void 상품생성() {
        Product product = new Product("test", BigDecimal.valueOf(5L));
        assertThat(product.getName()).isEqualTo("test");
    }

    @Test
    void 이름없는_상품생성_예외발생() {
        assertThatThrownBy(() -> new Product(null, BigDecimal.valueOf(5L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가격없는_상품생성_예외발생() {
        assertThatThrownBy(() -> new Product("test", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음수금액_상품생성_예외발생() {
        assertThatThrownBy(() -> new Product("test", BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}