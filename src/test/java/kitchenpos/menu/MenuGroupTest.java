package kitchenpos.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.menu.domain.MenuGroup;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

    @Test
    void 메뉴그룹생성() {
        MenuGroup menuGroup = new MenuGroup("test");
        assertThat(menuGroup.getName()).isEqualTo("test");
    }

    @Test
    void 이름없는_메뉴그룹생성_예외발생() {
        assertThatThrownBy(() -> new MenuGroup(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

}