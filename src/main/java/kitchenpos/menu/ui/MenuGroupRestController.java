package kitchenpos.menu.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.dto.MenuGroupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu-groups")
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroup> create(@RequestBody final MenuGroupRequest menuGroupRequest) {
        final MenuGroup created = menuGroupService.create(menuGroupRequest);
        final URI uri = URI.create("/api/menu-groups/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroup>> list() {
        return ResponseEntity.ok().body(menuGroupService.list());
    }
}
