package com.jujeol.member.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.jujeol.AcceptanceTest;
import com.jujeol.member.application.dto.MemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("멤버 조회 - 성공")
    public void findMember() {
        //when
        Long id = request().get("/members/me")
                .withDocument("member/find")
                .withUser()
                .build()
                .convertBody(MemberResponse.class)
                .getId();

        //then
        assertThat(id).isEqualTo(1L);

    }
}
