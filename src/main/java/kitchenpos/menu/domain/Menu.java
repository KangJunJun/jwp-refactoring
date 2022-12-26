package kitchenpos.menu.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.flywaydb.core.internal.util.StringUtils;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Long menuGroupId;

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    protected Menu() {
    }

    public Menu(String name, BigDecimal price, Long menuGroupId) {
        validateName(name);
        validatePrice(price);
        validateMenuGroupId(menuGroupId);
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
    }

    public Menu(Long id, String name, BigDecimal price, Long menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.get();
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("메뉴 이름은 필수값 입니다.");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴 가격은 0보다 커야 합니다");
        }
    }

    private void validateMenuGroupId(Long menuGroupId) {
        if (Objects.isNull(menuGroupId) || menuGroupId == 0) {
            throw new IllegalArgumentException("그룹아이디는 필수값 입니다");
        }
    }

    public void addMenuProduct(List<MenuProduct> menuProducts) {
        validateTotalPrice(menuProducts);
        this.menuProducts.addAll(this, menuProducts);
    }

    private void validateTotalPrice(List<MenuProduct> menuProducts) {
        BigDecimal amount = calculateAmount(menuProducts);
        if (price.compareTo(amount) > 0) {
            throw new IllegalArgumentException("상품들 금액의 합이 메뉴 가격보다 클 수 없습니다.");
        }
    }

    private BigDecimal calculateAmount(List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(MenuProduct::calculateAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }
}
