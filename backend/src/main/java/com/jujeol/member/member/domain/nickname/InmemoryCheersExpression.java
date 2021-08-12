package com.jujeol.member.member.domain.nickname;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum InmemoryCheersExpression {
    BLUE_JEAN("청춘은 바로 지금!", "청바지"),
    SAUNA("사랑과 우정을 나누자!", "사우나"),
    WILD_FLOWER("야심차고 생생하고 화끈하게!", "야생화"),
    CIDAR("사랑합니다 이평생 다바쳐서!", "사이다"),
    DONG_SAMUSO("동료를 사랑하는 것이 무엇보다 소중하다!", "동사무소"),
    HAN_WOO("한마음으로 우리는 갈수록 비상한다!", "한우갈비"),
    CHAN_CHAN("희망찬 활기찬 가득찬!", "찬찬찬"),
    JIN_DAL_RAE("진하고 달콤한 내일을 위하여!", "진달래"),
    DSHONG_MASHONG("드숑! 마숑!", "드숑마숑"),
    UHAHA("우리는 하늘 아래 하나다!", "우하하"),
    BYUN_SADDO("변함없는 사랑으로 내일 또 만납시다!", "변사또"),
    BAKKAS("박력있게 카리스마있게 스피디하게 원샷!", "박카스");

    InmemoryCheersExpression(String expression, String abbreviation) {
        this.cheersExpression = new CheersExpression(expression, abbreviation);
    }

    private CheersExpression cheersExpression;

    public static List<InmemoryCheersExpression> getCheersExpressions() {
        return Arrays.stream(InmemoryCheersExpression.values())
                .collect(Collectors.toList());
    }
}
