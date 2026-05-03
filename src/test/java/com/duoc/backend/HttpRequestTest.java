package com.duoc.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLoginSuccess() {
        // Arrange
        String baseUrl = "http://localhost:" + port + "/login?user=admin&encryptedPass=admin123";

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, null, String.class);

        // Assert
        System.out.println("Response: " + response.getStatusCode());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println("Token: " + response.getBody());
    }

    @Test
    void testLoginInvalidPassword() {
        // Arrange
        String baseUrl = "http://localhost:" + port + "/login?user=admin&encryptedPass=wrongpassword";

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, null, String.class);

        // Assert
        assertNotEquals(HttpStatus.OK, response.getStatusCode());
    }
}