package com.jujeol.admin;

import com.jujeol.admin.ui.dto.AdminDrinkRequest;

public class AdminDrinkRequestContainer {

    private static final AdminDrinkRequest OB = new AdminDrinkRequest("오비", "OB", 85.0,
            "KakaoTalk_Image_2021-07-08-19-58-22_007.png", 1L);
    private static final AdminDrinkRequest TIGER_LEMON = new AdminDrinkRequest("타이거 라들러 레몬",
            "Tiger_Lemon", 4.5,
            "KakaoTalk_Image_2021-07-08-19-58-22_008.png", 1L);
    private static final AdminDrinkRequest STELLA = new AdminDrinkRequest("스텔라", "stella", 5.5,
            "KakaoTalk_Image_2021-07-08-19-58-09_001.png", 1L);
    private static final AdminDrinkRequest KGB = new AdminDrinkRequest("KGB", "", 3.5,
            "KakaoTalk_Image_2021-07-08-19-58-09_002.png", 1L);
    private static final AdminDrinkRequest ESTP = new AdminDrinkRequest("ESTP", "", 7.5,
            "KakaoTalk_Image_2021-07-08-19-58-11_003.png", 1L);
    private static final AdminDrinkRequest TIGER_RAD = new AdminDrinkRequest("타이거 라들러 자몽",
            "Tiger_Rad", 9.5, "KakaoTalk_Image_2021-07-08-19-58-15_004.png", 1L);
    private static final AdminDrinkRequest TSINGTAO = new AdminDrinkRequest("칭따오", "TSINGTAO", 12.0,
            "KakaoTalk_Image_2021-07-08-19-58-18_005.png", 1L);
    private static final AdminDrinkRequest APPLE = new AdminDrinkRequest("애플", "Apple", 8.2,
            "KakaoTalk_Image_2021-07-08-19-58-20_006.png", 1L);

    public static AdminDrinkRequest stella() {
        return STELLA;
    }

    public static AdminDrinkRequest kgb() {
        return KGB;
    }

    public static AdminDrinkRequest estp() {
        return ESTP;
    }

    public static AdminDrinkRequest tigerRad() {
        return TIGER_RAD;
    }

    public static AdminDrinkRequest tsingtao() {

        return TSINGTAO;
    }

    public static AdminDrinkRequest apple() {
        return APPLE;
    }

    public static AdminDrinkRequest ob() {
        return OB;
    }

    public static AdminDrinkRequest tigerLemon() {
        return TIGER_LEMON;
    }
}
