package com.jujeol.member.application;

import com.jujeol.member.domain.Member;
import com.jujeol.member.domain.MemberRepository;
import com.jujeol.member.domain.nickname.Nickname;
import com.jujeol.member.domain.nickname.NicknameFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddHashNicknameFactory implements NicknameFactory {

    private static final PageRequest FIRST_OBJECT = PageRequest.of(0, 1);
    private static final String DELIMITER = "_";
    private static final String HUNDREDS_PLACE_PATTERN = "%03d";
    private static final int EXIST = 0;

    private final MemberRepository memberRepository;

    @Override
    public Nickname createNickname(String nickname) {
        int lastHashOfNickname = findLastHashOfNickname(nickname);
        String newHash = String.format(HUNDREDS_PLACE_PATTERN, lastHashOfNickname + 1);

        return Nickname.create(nickname + DELIMITER + newHash);
    }

    private int findLastHashOfNickname(String nickname) {
        List<Member> existMember = memberRepository
                .findOneStartingWithNicknameAndMostRecent(nickname, FIRST_OBJECT);

        if (existMember.isEmpty()) {
            return 0;
        }

        return parsedHash(existMember.get(EXIST));
    }

    private int parsedHash(Member member) {
        String nickname = member.getNickname().getNickname();
        int indexOfDelimiter = nickname.indexOf(DELIMITER);
        if (notFoundOrLastCharacter(nickname, indexOfDelimiter)) {
            return 0;
        }

        return Integer.parseInt(nickname.substring(indexOfDelimiter + 1));
    }

    private boolean notFoundOrLastCharacter(String nickname, int indexOfDelimiter) {
        return indexOfDelimiter == -1 || indexOfDelimiter == nickname.length() - 1;
    }
}
