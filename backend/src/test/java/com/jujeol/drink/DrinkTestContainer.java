package com.jujeol.drink;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DrinkTestContainer {
    OB(new AdminDrinkRequest("오비", "OB", 8.5, "BEER",
        "맥주의 청량감이 느껴지는 맥주이다.")),
    TIGER_LEMON(new AdminDrinkRequest("타이거 라들러 레몬", "Tiger_Lemon", 4.5,
        "BEER", "레몬맛이 느껴지는 맥주이다.")),
    STELLA(new AdminDrinkRequest("스텔라", "stella", 5.5,
        "BEER", "스텔라만의 맛이 느껴지는 맥주이다.")),
    KGB(new AdminDrinkRequest("KGB", "", 3.5, "BEER",
        "KGB는 맥주가 아니다.")),
    ESTP(new AdminDrinkRequest("ESTP", "", 7.5,
        "BEER", "ESTP 맛이 나는 맥주이다.")),
    TIGER_RAD(new AdminDrinkRequest("타이거 라들러 자몽", "Tiger_Rad", 9.5,
        "BEER", "라드 맛이 나는 맥주이다.")),
    TSINGTAO(new AdminDrinkRequest("칭따오", "TSINGTAO", 12.0,
        "BEER", "양꼬치와 잘어울리는 맥주이다.")),
    APPLE(new AdminDrinkRequest("애플", "Apple", 8.2,
        "BEER", "사과맛이 나는 맥주이다.")),
    WINE(new AdminDrinkRequest("와인", "Wine", 8.2,
        "WINE", "테스트1 상세설명")),
    TEST_TWO(new AdminDrinkRequest("테스트2", "Test2", 8.2,
        "BEER", "테스트2 상세설명")),
    TEST_THREE(new AdminDrinkRequest("테스트3", "Test3", 8.2,
        "BEER", "테스트3 상세설명")),
    TEST_FOUR(new AdminDrinkRequest("테스트4", "Test4", 8.2,
        "BEER", "테스트4 상세설명")),
    TEST_FIVE(new AdminDrinkRequest("테스트5", "Test5", 8.2,
        "BEER", "테스트5 상세설명")),
    TEST_SIX(new AdminDrinkRequest("테스트6", "Test6", 8.2,
        "BEER", "테스트6 상세설명")),
    ;

    private final AdminDrinkRequest adminDrinkRequest;

    DrinkTestContainer(AdminDrinkRequest adminDrinkRequest) {
        this.adminDrinkRequest = adminDrinkRequest;
    }

    public static List<AdminDrinkRequest> asAdminRequestList(
        DrinkTestContainer... drinkTestContainer) {
        return Arrays.stream(drinkTestContainer).map(DrinkTestContainer::getAdminDrinkRequest)
            .collect(Collectors.toList());
    }

    public static List<String> asNames(DrinkTestContainer... drinkTestContainers) {
        return Arrays.stream(drinkTestContainers).map(DrinkTestContainer::getName)
            .collect(Collectors.toList());
    }

    public AdminDrinkRequest getAdminDrinkRequest() {
        return adminDrinkRequest;
    }

    public String getName() {
        return adminDrinkRequest.getName();
    }
}
