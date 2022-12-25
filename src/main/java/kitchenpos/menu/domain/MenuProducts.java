package kitchenpos.menu.domain;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class MenuProducts {
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected MenuProducts() {
    }

    private void add(Menu menu, MenuProduct menuProduct) {
        if (!this.menuProducts.contains(menuProduct)) {
            menuProducts.add(menuProduct);
        }
        menuProduct.bindTo(menu);
    }

    public void addAll(Menu menu, List<MenuProduct> menuProducts) {
        requireNonNull(menu, "menu");
        requireNonNull(menuProducts, "menuProducts");
        for (MenuProduct menuProduct : menuProducts) {
            add(menu, menuProduct);
        }
    }

    public List<MenuProduct> get() {
        return Collections.unmodifiableList(menuProducts);
    }
}