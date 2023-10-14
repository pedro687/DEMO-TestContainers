package com.example.demo.service;

import com.example.demo.controller.NotaFiscalDto;
import com.example.demo.repository.NotaFiscalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotaFiscalServiceTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    NotaFiscalRepository notaFiscalRepository;

    @Container
    static KafkaContainer kafkaContainer  = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:latest")
    );

    @Container
    static MySQLContainer mySQLContainer  = new MySQLContainer<>(
            DockerImageName.parse("mysql:8.0-debian")
    );

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        registry.add("spring.datasource.driverClassName", () -> mySQLContainer.getDriverClassName());
        registry.add("spring.datasource.username", () -> mySQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> mySQLContainer.getPassword());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");

    }

    @Test
    void test_containers() {
        Assertions.assertNotNull(kafkaContainer);
        Assertions.assertNotNull(mySQLContainer);
    }

    @Test
    void e2eTest() throws URISyntaxException {
        var notaFiscalDto = NotaFiscalDto.of("12345", LocalDate.now(), 1L, BigDecimal.valueOf(22.50));
        final var stringBaseUrl = "/nota-fiscal/emitir";
        URI uri = new URI(stringBaseUrl);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<NotaFiscalDto> request = new HttpEntity<>(notaFiscalDto, headers);

        var response = this.testRestTemplate.postForEntity(uri, request, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        var findInDb = notaFiscalRepository.findById(1L);

        assertTrue(findInDb.isPresent());
        assertEquals(notaFiscalDto.number(), findInDb.get().getNumero());
    }
}