package com.jujeol.member.domain.nickname;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum InmemoryCheersExpression {
    BLUE_JEAN("청춘은 바로 지금", "청바지"),
    SAUNA("사랑과 우정을 나누자", "사우나"),
    WILD_FLOWER("야심차고 생생하고 화끈하게", "야생화");

    InmemoryCheersExpression(String expression, String abbreviation) {
        this.cheersExpression = new CheersExpression(expression, abbreviation);
    }

    private CheersExpression cheersExpression;

    public static List<InmemoryCheersExpression> getCheersExpressions() {
        return Arrays.stream(InmemoryCheersExpression.values())
                .collect(Collectors.toList());
    }
}
