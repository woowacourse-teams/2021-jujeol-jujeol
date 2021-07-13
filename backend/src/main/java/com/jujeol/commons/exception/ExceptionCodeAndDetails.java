package com.jujeol.commons.exception;

import com.jujeol.drink.exception.InvalidAlcoholByVolumeException;
import com.jujeol.drink.exception.InvalidDrinkNameException;
import com.jujeol.member.exception.KakaoAccessException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.servlet.NoHandlerFoundException;

@AllArgsConstructor
@Getter
public enum ExceptionCodeAndDetails {
    NOT_FOUND_ERROR_CODE("0001", "발생한 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),
    NOT_FOUND_API("0002", "해당 경로에 대한 응답 API를 찾을 수 없습니다.", NoHandlerFoundException.class),

    INVALID_ALCOHOL_BY_VOLUME("2001", "해당 주류의 도수가 잘 못 되었습니다.", InvalidAlcoholByVolumeException.class),
    INVALID_DRINK_NAME("2002", "해당 주류의 이름이 공백입니다.", InvalidDrinkNameException.class),
  
    KAKAO_ACCESS_EXCEPTION("1002", "카카오 로그인 서버에 접근 중 예외가 발생했습니다.", KakaoAccessException.class)
    ;

    private String code;
    private String message;
    private Class<? extends Exception> type;

    public static ExceptionCodeAndDetails findByClass(Class<? extends Exception> type) {
        return Arrays.stream(ExceptionCodeAndDetails.values())
                .filter(code -> code.type.equals(type))
                .findAny()
                .orElseThrow(NotFoundErrorCodeException::new);
    }

}
