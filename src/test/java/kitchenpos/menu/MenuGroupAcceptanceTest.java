package kitchenpos.menu;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.AcceptanceTest;
import kitchenpos.menu.domain.MenuGroup;
import kitchenpos.menu.dto.MenuGroupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("메뉴 그룹 관련 기능")
public class MenuGroupAcceptanceTest extends AcceptanceTest {

    /**
     * Feature: 메뉴 그룹 기능
     *  Scenario: 메뉴 그룹 추가 조회
     *      when: 메뉴 그룹을 생성한다
     *      then: 메뉴 그룹이 생성됨
     *      when: 이름이 없는 메뉴 그룹을 생성한다
     *      then: 메뉴 그룹 생성이 실패됨           
     *      when: 메뉴 그룹 목록을 조회한다.
     *      then: 메뉴 그룹 목록이 조회됨
     */

    @DisplayName("메뉴 그룹 추가 조회")
    @Test
    void scenarioMenuGroup() {
        // when
        ExtractableResponse<Response> 메뉴그룹 = 메뉴_그룹_생성_요청("메뉴그룹");
        // then
        메뉴_그룹_생성됨(메뉴그룹);

        // when
        ExtractableResponse<Response> 이름없음 = 메뉴_그룹_생성_요청(null);
        // then
        메뉴_그룹_생성_실패됨(이름없음);

        // when
        ExtractableResponse<Response> response = 메뉴_그룹_목록_조회_요청();
        // then
        메뉴_그룹_목록_조회됨(response, "메뉴그룹");
        
    }


    public static ExtractableResponse<Response> 메뉴_그룹_생성_요청(String name) {
        MenuGroupRequest request = new MenuGroupRequest(name);
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/api/menu-groups")
                .then().log().all()
                .extract();
    }


    public static ExtractableResponse<Response> 메뉴_그룹_목록_조회_요청() {
        return RestAssured
                .given().log().all()
                .when().get("/api/menu-groups")
                .then().log().all()
                .extract();
    }

    public static void 메뉴_그룹_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 메뉴_그룹_생성_실패됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


    public static void 메뉴_그룹_목록_조회됨(ExtractableResponse<Response> response, String... names) {
        List<MenuGroup> menuGroups = response.jsonPath().getList(".", MenuGroup.class);

        List<String> productNames = menuGroups.stream()
                .map(MenuGroup::getName)
                .collect(Collectors.toList());
        assertThat(productNames).containsExactly(names);
    }
}
