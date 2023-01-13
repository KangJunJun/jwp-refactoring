package kitchenpos.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import kitchenpos.menu.domain.Menu;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    void 메뉴생성() {
        Menu menu = new Menu("test", BigDecimal.valueOf(5L), 1L);
        assertThat(menu.getName()).isEqualTo("test");
    }

    @Test
    void 이름없는_메뉴생성_예외발생() {
        assertThatThrownBy(() -> new Menu(null,  BigDecimal.valueOf(5L), 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가격없는_메뉴생성_예외발생() {
        assertThatThrownBy(() -> new Menu("test", null, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음수금액_메뉴생성_예외발생() {
        assertThatThrownBy(() -> new Menu("test", BigDecimal.valueOf(-1L), 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 그룹없는_메뉴생성_예외발생() {
        assertThatThrownBy(() -> new Menu("test", BigDecimal.valueOf(-1L), null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}