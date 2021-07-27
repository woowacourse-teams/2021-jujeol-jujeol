package com.jujeol.drink;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum DrinkTestContainer {
    OB(new AdminDrinkRequest("오비", "OB", 85.0, "KakaoTalk_Image_2021-07-08-19-58-22_007.png", 1L)),
    TIGER_LEMON(new AdminDrinkRequest("타이거 라들러 레몬", "Tiger_Lemon", 4.5, "KakaoTalk_Image_2021-07-08-19-58-22_008.png", 1L)),
    STELLA(new AdminDrinkRequest("스텔라", "stella", 5.5, "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 1L)),
    KGB(new AdminDrinkRequest("KGB", "", 3.5, "KakaoTalk_Image_2021-07-08-19-58-09_002.png", 1L)),
    ESTP(new AdminDrinkRequest("ESTP", "", 7.5, "KakaoTalk_Image_2021-07-08-19-58-11_003.png", 1L)),
    TIGER_RAD(new AdminDrinkRequest("타이거 라들러 자몽", "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png", 1L)),
    TSINGTAO(new AdminDrinkRequest("칭따오", "TSINGTAO", 12.0, "KakaoTalk_Image_2021-07-08-19-58-18_005.png", 1L)),
    APPLE(new AdminDrinkRequest("애플", "Apple", 8.2, "KakaoTalk_Image_2021-07-08-19-58-20_006.png", 1L));

    private final AdminDrinkRequest adminDrinkRequest;

    DrinkTestContainer(AdminDrinkRequest adminDrinkRequest) {
        this.adminDrinkRequest = adminDrinkRequest;
    }

    public static List<AdminDrinkRequest> asAdminRequestList(DrinkTestContainer... drinkTestContainer) {
        return Arrays.stream(drinkTestContainer).map(DrinkTestContainer::getAdminDrinkRequest)
                .collect(Collectors.toList());
    }

    public AdminDrinkRequest getAdminDrinkRequest() {
        return adminDrinkRequest;
    }

    public String getName() {
        return adminDrinkRequest.getName();
    }
}
