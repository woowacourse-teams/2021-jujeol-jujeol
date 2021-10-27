package com.jujeol.member.member.application;

import com.jujeol.member.member.domain.Member;
import com.jujeol.member.member.domain.nickname.Nickname;
import com.jujeol.member.member.domain.nickname.NicknameCreator;
import com.jujeol.member.member.domain.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddHashNicknameCreator implements NicknameCreator {

    private static final PageRequest FIRST_OBJECT = PageRequest.of(0, 1);
    private static final String DELIMITER = "_";
    private static final String HUNDREDS_PLACE_PATTERN = "%03d";
    private static final int EXIST = 0;

    private final MemberRepository memberRepository;

    @Override
    public Nickname createNickname(String nicknamePrefix) {
        int lastHashOfNickname = findLastHashOfNickname(nicknamePrefix);

        String newHash = nextHash(lastHashOfNickname);

        String nickname = assembleNickname(nicknamePrefix, newHash);

        String validatedNickname = ifExistCreateNewHash(nickname, nicknamePrefix);

        return Nickname.create(validatedNickname);
    }

    private String assembleNickname(String nicknamePrefix, String newHash) {
        return nicknamePrefix + DELIMITER + newHash;
    }

    private String ifExistCreateNewHash(String nickname, String nicknamePrefix) {
        if (memberRepository.existsByNicknameNickname(nickname)) {
            return ifExistCreateNewHash(
                    assembleNickname(nicknamePrefix, nextHash(parsedHash(nickname)))
                    , nicknamePrefix);
        }
        return nickname;
    }

    private int findLastHashOfNickname(String nickname) {
        List<Member> existMember = memberRepository
                .findOneStartingWithNicknameAndMostRecent(nickname, FIRST_OBJECT);

        if (existMember.isEmpty()) {
            return 0;
        }

        return parsedHash(existMember.get(EXIST).getNickname().getNickname());
    }

    private int parsedHash(String nickname) {
        int indexOfDelimiter = nickname.indexOf(DELIMITER);
        if (notFoundOrLastCharacter(nickname, indexOfDelimiter)) {
            return 0;
        }

        return Integer.parseInt(nickname.substring(indexOfDelimiter + 1));
    }

    private boolean notFoundOrLastCharacter(String nickname, int indexOfDelimiter) {
        return indexOfDelimiter == -1 || indexOfDelimiter == nickname.length() - 1;
    }

    private String nextHash(int lastHashOfNickname) {
        return String.format(HUNDREDS_PLACE_PATTERN, lastHashOfNickname + 1);
    }
}
