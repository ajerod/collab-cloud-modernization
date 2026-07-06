package com.collab.workspace_service.integration;

import com.collab.workspace_service.adapter.in.web.response.WorkspaceListResponse;
import com.collab.workspace_service.adapter.in.web.response.WorkspaceResponse;
import com.collab.workspace_service.common.error.WorkspaceErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class WorkspaceResourceIT {

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("collab_workspace_it")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void registerDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.locations", () -> "classpath:db/migration");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateWorkspace() {
        ResponseEntity<WorkspaceResponse> response = createWorkspace(
                "Architecture Workspace",
                "user-001"
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().id());
        assertEquals("Architecture Workspace", response.getBody().name());
        assertEquals("user-001", response.getBody().ownerId());
        assertNotNull(response.getBody().createdAt());
    }

    @Test
    void shouldGetWorkspaceById() {
        ResponseEntity<WorkspaceResponse> createdResponse = createWorkspace(
                "Workspace to retrieve",
                "user-002"
        );

        assertEquals(HttpStatus.CREATED, createdResponse.getStatusCode());
        assertNotNull(createdResponse.getBody());

        String workspaceId = createdResponse.getBody().id();

        ResponseEntity<WorkspaceResponse> getResponse = restTemplate.getForEntity(
                workspaceUrl("/api/workspaces/" + workspaceId),
                WorkspaceResponse.class
        );

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(workspaceId, getResponse.getBody().id());
        assertEquals("Workspace to retrieve", getResponse.getBody().name());
        assertEquals("user-002", getResponse.getBody().ownerId());
    }

    @Test
    void shouldListWorkspacesWithPagination() {
        createWorkspace("Workspace A", "user-001");
        createWorkspace("Workspace B", "user-002");

        URI uri = UriComponentsBuilder
                .fromUriString(workspaceUrl("/api/workspaces"))
                .queryParam("page", 0)
                .queryParam("size", 10)
                .build()
                .toUri();

        ResponseEntity<WorkspaceListResponse> response = restTemplate.getForEntity(
                uri,
                WorkspaceListResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().page());
        assertEquals(10, response.getBody().size());
        assertTrue(response.getBody().totalElements() >= 2);
        assertFalse(response.getBody().items().isEmpty());
    }

    @Test
    void shouldReturnNotFoundWhenWorkspaceDoesNotExist() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                workspaceUrl("/api/workspaces/00000000-0000-0000-0000-000000000000"),
                String.class
        );

        String responseBody = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertNotNull(responseBody),
                () -> assertTrue(
                        responseBody.contains("\"status\":404"),
                        responseBody
                ),
                () -> assertTrue(
                        responseBody.contains("\"title\":\"Workspace not found\""),
                        responseBody
                ),
                () -> assertTrue(
                        responseBody.contains("\"type\":\"https://collab-cloud-modernization/errors/workspace-not-found\""),
                        responseBody
                ),
                () -> assertTrue(
                        responseBody.contains("Workspace not found with id"),
                        responseBody
                )
        );
    }

    @Test
    void shouldReturnBadRequestWhenWorkspaceIdIsInvalid() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                workspaceUrl("/api/workspaces/not-a-uuid"),
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(WorkspaceErrorCode.INVALID_WORKSPACE_ID.code()));
    }

    @Test
    void shouldReturnBadRequestWhenCreateWorkspacePayloadIsInvalid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = Map.of(
                "name", "",
                "ownerId", "user-001"
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                workspaceUrl("/api/workspaces"),
                request,
                String.class
        );

        String responseBody = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertNotNull(responseBody),
                () -> assertTrue(
                        responseBody.contains("\"status\":400"),
                        responseBody
                ),
                () -> assertTrue(
                        responseBody.contains("\"title\":\"Validation error\""),
                        responseBody
                ),
                () -> assertTrue(
                        responseBody.contains("\"type\":\"https://collab-cloud-modernization/errors/validation-error\""),
                        responseBody
                ),
                () -> assertTrue(
                        responseBody.contains("name: Workspace name must not be blank"),
                        responseBody
                )
        );
    }

    @Test
    void shouldReturnBadRequestWhenPaginationIsInvalid() {
        URI uri = UriComponentsBuilder
                .fromUriString(workspaceUrl("/api/workspaces"))
                .queryParam("page", -1)
                .queryParam("size", 20)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(
                uri,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains(WorkspaceErrorCode.INVALID_PAGINATION.code()));
    }

    private ResponseEntity<WorkspaceResponse> createWorkspace(String name, String ownerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> payload = Map.of(
                "name", name,
                "ownerId", ownerId
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        return restTemplate.postForEntity(
                workspaceUrl("/api/workspaces"),
                request,
                WorkspaceResponse.class
        );
    }

    private String workspaceUrl(String path) {
        return "http://localhost:" + port + path;
    }
}