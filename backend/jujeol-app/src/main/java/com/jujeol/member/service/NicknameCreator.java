package com.jujeol.member.service;

import com.jujeol.member.domain.model.Member;
import com.jujeol.member.domain.model.Nickname;
import com.jujeol.member.domain.reader.MemberReader;
import com.jujeol.member.rds.repository.MemberPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class NicknameCreator {

    private static final PageRequest FIRST_OBJECT = PageRequest.of(0, 1);
    private static final String DELIMITER = "_";
    private static final String HUNDREDS_PLACE_PATTERN = "%03d";

    private final MemberReader memberReader;
    private final MemberPageRepository memberPageRepository;

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
        if (memberReader.existsByNickname(nickname)) {
            return ifExistCreateNewHash(
                    assembleNickname(nicknamePrefix, nextHash(parsedHash(nickname)))
                    , nicknamePrefix);
        }
        return nickname;
    }

    private int findLastHashOfNickname(String nickname) {
        Optional<Member> existMember = memberPageRepository.findByNickname(nickname, FIRST_OBJECT);

        if (existMember.isEmpty()) {
            return 0;
        }

        return parsedHash(existMember.get().getNickname().value());
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
