package com.yohwan.tutor.integration;

import com.yohwan.tutor.annotation.IntegrationTest;
import com.yohwan.tutor.data.QuestionTest;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.dto.request.QuestionSaveRequest;
import com.yohwan.tutor.dto.request.QuestionUpdateRequest;
import com.yohwan.tutor.dto.response.QuestionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class QuestionControllerIntegrationTest extends QuestionTest {
    private static final String HOST_URL = "http://localhost:";
    private static final String BASE_URL = "/api/questions";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("문제들의 목록을 조회한다.")
    void listQuestions() {
        HttpHeaders requestHeaders = getHttpHeaders();

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        String url = getUrl();

        ResponseEntity<ApiResponse<List<QuestionResponse>>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData()).hasSize(9);
    }

    @Test
    @DisplayName("문제를 조회한다.")
    void getQuestion() {
        HttpHeaders requestHeaders = getHttpHeaders();

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long questionId = 1L;
        String url = getUrl() + "/" + questionId;

        ResponseEntity<ApiResponse<QuestionResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData().getId()).isEqualTo(questionId);
        assertThat(result.getBody().getData().getLevel()).isEqualTo(1);
        assertThat(result.getBody().getData().getDescription()).isEqualTo("쉬운 문제 1");
    }

    @Test
    @DisplayName("문제를 등록한다.")
    void saveQuestion() throws Exception {
        HttpHeaders requestHeaders = getHttpHeaders();

        QuestionSaveRequest request = saveRequest();

        HttpEntity<QuestionSaveRequest> requestHttpEntity = new HttpEntity<>(request, requestHeaders);

        String url = getUrl();

        ResponseEntity<ApiResponse<QuestionResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getData().getLevel()).isEqualTo(request.getLevel());
        assertThat(result.getBody().getData().getDescription()).isEqualTo(request.getDescription());
    }

    @Test
    @DisplayName("문제를 수정한다.")
    void updateQuestion() throws Exception {
        HttpHeaders requestHeaders = getHttpHeaders();

        QuestionUpdateRequest request = updateRequest();

        HttpEntity<QuestionUpdateRequest> requestHttpEntity = new HttpEntity<>(request, requestHeaders);

        long questionId = 1L;
        String url = getUrl() + "/" + questionId;

        ResponseEntity<Void> result = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ApiResponse<QuestionResponse>> finalResult = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(finalResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(finalResult.getBody().getData().getLevel()).isEqualTo(request.getLevel());
        assertThat(finalResult.getBody().getData().getDescription()).isEqualTo(request.getDescription());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", "1");
        return requestHeaders;
    }

    private String getUrl() {
        return HOST_URL + port + BASE_URL;
    }
}
