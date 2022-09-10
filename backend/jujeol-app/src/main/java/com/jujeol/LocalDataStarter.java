package com.jujeol;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"local"})
@RequiredArgsConstructor
public class LocalDataStarter implements CommandLineRunner {

    private final DataLoader dataLoader;

    @Override
    public void run(String... args) throws Exception {
        dataLoader.loadData();
    }
}
