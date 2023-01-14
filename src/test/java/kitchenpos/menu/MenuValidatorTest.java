package kitchenpos.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.menu.domain.MenuGroupRepository;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.validator.MenuValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Menu Validator 테스트")
@ExtendWith(MockitoExtension.class)
class MenuValidatorTest {
    @InjectMocks
    private MenuValidator menuValidator;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    MenuValidatorTest() {}

    @Test
    @DisplayName("메뉴를 생성할때 정상적으로 유효성 검사가 성공 된다")
    void validateCreateMenu() {
        // given
        MenuProductRequest menuProductRequest = MenuProductRequest.of(1L, 1);
        MenuRequest menuRequest = MenuRequest.of(
                "불고기정식",
                BigDecimal.valueOf(12_000),
                1L,
                Arrays.asList(menuProductRequest)
        );

        when(menuGroupRepository.existsById(any())).thenReturn(true);

        // when & then
        assertDoesNotThrow(() -> menuValidator.createMenu(menuRequest));
    }

    @Test
    @DisplayName("메뉴의 메뉴그룹 id가 존재하지 않으면 예외가 발생된다.")
    void createMenuGroupNotExists() {
        // given
        MenuProductRequest menuProductRequest = MenuProductRequest.of(1L, 1);
        MenuRequest menuRequest = MenuRequest.of(
                "불고기정식",
                BigDecimal.valueOf(12_000),
                1L,
                Arrays.asList(menuProductRequest)
        );

        when(menuGroupRepository.existsById(any())).thenReturn(false);

        assertThatThrownBy(() -> menuValidator.createMenu(menuRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 메뉴그룹입니다.");
    }
}