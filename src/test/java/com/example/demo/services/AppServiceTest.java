package com.example.demo.services;

import com.example.demo.models.Application;
import com.example.demo.repositories.MyUserRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.IntStream;

public class AppServiceTest {
    private List<Application> applications;
    @Test
    void whenClassHasFinalMembers_thenGeneratedConstructorHasParameters() {
        loadAppInDB();
        var repositoryMock = Mockito.mock(MyUserRepository.class);
        var passwordEncoderMock = Mockito.mock(PasswordEncoder.class);
        AppService classWithMembers = new AppService(applications, repositoryMock, passwordEncoderMock);

        Assertions.assertNotNull(classWithMembers);
    }

    public void loadAppInDB(){
        Faker faker = new Faker();
        applications = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Application.builder()
                        .id(i)
                        .name(faker.app().name())
                        .author(faker.app().author())
                        .version(faker.app().version())
                        .build()).toList();
    }
}
