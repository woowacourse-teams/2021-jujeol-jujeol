package com.jujeol.drink.listener;

import com.jujeol.drink.domain.usecase.DrinkPreferenceUpdateUseCase;
import com.jujeol.drink.domain.usecase.command.DrinkPreferenceUpdateCommand;
import com.jujeol.feedback.domain.event.PreferenceEvent;
import com.jujeol.feedback.domain.model.Preference;
import com.jujeol.feedback.domain.reader.PreferenceReader;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PreferenceEventListener {

    private final DrinkPreferenceUpdateUseCase drinkPreferenceUpdateUseCase;
    private final PreferenceReader preferenceReader;

    @EventListener
    public void listenPreferenceEvent(PreferenceEvent event) {
        List<Preference> preferences = preferenceReader.findByDrinkId(event.getDrinkId());
        double preferenceAvg = preferences.stream()
                                          .mapToDouble(Preference::getRate)
                                          .average()
                                          .orElse(0);
        drinkPreferenceUpdateUseCase.updateDrinkPreference(
                DrinkPreferenceUpdateCommand.builder()
                                            .drinkId(event.getDrinkId())
                                            .preferenceAvg(preferenceAvg)
                                            .build()
        );
    }
}
