package com.jujeol.test.domain.executor;

import com.jujeol.test.domain.model.command.DrinkCommand;
import com.jujeol.test.domain.model.usecase.DrinkChangeNameUseCase;
import com.jujeol.test.domain.model.usecase.DrinkRegistrationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class DrinkExecutor implements DrinkRegistrationUseCase, DrinkChangeNameUseCase {

    private final DrinkRegistrationUseCase.DrinkInsertPort registrationDrinkPort;
    private final DrinkRegistrationUseCase.DrinkSearchPort searchDrinkPort;

    private final DrinkChangeNameUseCase.DrinkPort changeNameDrinkPort;

    @Override
    @Transactional
    public void register(DrinkCommand drinkCommand) {
        // 도메인 로직이 뭐가 있을까요... 이미 존재하는 음료..? 혹은 뭔가 엔티티에 넣을만한게 부족한 경우..?

        registrationDrinkPort.insert();
    }

    @Override
    public void changeName(String name) {
        changeNameDrinkPort.modifyName(name);
    }
}
