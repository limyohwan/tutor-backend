package com.yohwan.tutor.integration;

import com.yohwan.tutor.annotation.IntegrationTest;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.data.GroupTest;
import com.yohwan.tutor.dto.response.GroupResponse;
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
public class GroupControllerIntegrationTest extends GroupTest {
    private static final String HOST_URL = "http://localhost:";
    private static final String BASE_URL = "/api/groups";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("인증 헤더에 들어온 값이 숫자형식이 아니면 예외를 발생한다.")
    void authorizationHeaderFormatIsNotNumberThrowsException() {
        HttpHeaders requestHeaders = getHttpHeaders("string");

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long groupId = 1L;
        String url = getUrl() + "/" + groupId ;

        ResponseEntity<ApiResponse<GroupResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("인증 헤더에 값이 없으면 예외를 발생한다.")
    void authorizationHeaderHasNoValueThrowsException() {
        HttpHeaders requestHeaders = getHttpHeaders("");

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long groupId = 1L;
        String url = getUrl() + "/" + groupId ;

        ResponseEntity<ApiResponse<GroupResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("인증 헤더에 나의 정보(선생님)가 옳바르지 않으면 예외를 발생한다")
    void authorizationHeaderHasNotExistedTeacherIdThrowsException() {
        HttpHeaders requestHeaders = getHttpHeaders("999999");

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long groupId = 1L;
        String url = getUrl() + "/" + groupId ;

        ResponseEntity<ApiResponse<GroupResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("그룹 정보를 조회한다.")
    void getGroup() {
        HttpHeaders requestHeaders = getHttpHeaders("1");

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long groupId = 1L;
        String url = getUrl() + "/" + groupId ;

        ResponseEntity<ApiResponse<GroupResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData().getName()).isEqualTo("쉬운 그룹");
        assertThat(result.getBody().getData().getStandardDescription()).isEqualTo("쉬운 문제들을 푸는 학생들 모임");
        assertThat(result.getBody().getData().getStudents()).hasSize(5);
    }

    @Test
    @DisplayName("그룹 정보를 조회 시 아이디가 옳바르지 않으면 예외를 발생한다.")
    void getGroupByNotExistedId() {
        HttpHeaders requestHeaders = getHttpHeaders("1");

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long groupId = 99999L;
        String url = getUrl() + "/" + groupId ;

        ResponseEntity<ApiResponse<GroupResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("그룹에서 추천하는 문제 목록을 조회한다.")
    void listRecommendQuestions() {
        HttpHeaders requestHeaders = getHttpHeaders("1");

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long groupId = 1L;
        String url = getUrl() + "/" + groupId + "/recommend-questions" ;

        ResponseEntity<ApiResponse<List<QuestionResponse>>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData()).hasSize(3);
    }

    private static HttpHeaders getHttpHeaders(String headerValue) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.add("Authorization", headerValue);
        return requestHeaders;
    }

    private String getUrl() {
        return HOST_URL + port + BASE_URL;
    }
}
