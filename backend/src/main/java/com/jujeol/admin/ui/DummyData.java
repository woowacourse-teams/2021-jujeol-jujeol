package com.jujeol.admin.ui;

import com.jujeol.admin.ui.dto.AdminDrinkResponse;
import com.jujeol.admin.ui.dto.CategoryResponse;
import java.util.Arrays;
import java.util.List;

public class DummyData {

    public static List<AdminDrinkResponse> drinkResponses() {
        final CategoryResponse 맥주 = new CategoryResponse(1L, "맥주");
        final CategoryResponse 보드카 = new CategoryResponse(2L, "보드카");


        return Arrays.asList(
                new AdminDrinkResponse(1L, "하이트", "hite", "thisIsUrl1", 맥주, 12.0),
                new AdminDrinkResponse(2L, "KGB", "KGB", "thisIsUrl2", 보드카, 4.0),
                new AdminDrinkResponse(3L, "필스너우르켈", "Pilsner Urquell", "thisIsUrl3", 맥주, 7.0),
                new AdminDrinkResponse(4L, "하이네켄", "Heineken", "thisIsUrl4", 맥주, 7.6),
                new AdminDrinkResponse(5L, "아사히", "Asahi", "thisIsUrl5", 맥주, 7.8),
                new AdminDrinkResponse(6L, "칭따오", "Tsingtao", "thisIsUrl6", 맥주, 9.0),
                new AdminDrinkResponse(7L, "호가든", "Hoegaarden", "thisIsUrl7", 맥주, 7.0)

        );
    }
}
