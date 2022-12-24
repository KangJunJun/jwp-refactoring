package kitchenpos.domain;

import org.flywaydb.core.internal.util.StringUtils;

public class MenuGroup {
    private Long id;
    private String name;

    public MenuGroup(String name) {
        validateName(name);
        this.name = name;
    }

    public MenuGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("상품 이름은 필수값 입니다.");
        }
    }
}
