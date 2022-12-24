package kitchenpos.menu.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import kitchenpos.menu.domain.MenuGroup;

public class MenuGroupRequest {
    private final String name;

    @JsonCreator
    public MenuGroupRequest(String name) {
        this.name = name;
    }

    public MenuGroup toMenuGroup() {
        return MenuGroup.from(name);
    }

    public String getName() {
        return name;
    }
}