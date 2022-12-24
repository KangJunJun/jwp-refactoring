package kitchenpos.menu.domain;

import javax.persistence.*;
import org.flywaydb.core.internal.util.StringUtils;

@Entity
public class MenuGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected MenuGroup(){
    }

    public MenuGroup(String name) {
        validateName(name);
        this.name = name;
    }

    public static MenuGroup from(String name) {
        return new MenuGroup(name);
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("메뉴 이름은 필수값 입니다.");
        }
    }
}
