package com.yohwan.tutor.integration;

import com.yohwan.tutor.annotation.IntegrationTest;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.data.StudentTest;
import com.yohwan.tutor.dto.request.StudentSaveRequest;
import com.yohwan.tutor.dto.request.StudentUpdateRequest;
import com.yohwan.tutor.dto.response.StudentResponse;
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
public class StudentControllerIntegrationTest extends StudentTest {
    private static final String HOST_URL = "http://localhost:";
    private static final String BASE_URL = "/api/students";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("학생들의 목록을 조회한다.")
    void listStudents() throws Exception {
        HttpHeaders requestHeaders = getHttpHeaders();

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        String url = getUrl();

        ResponseEntity<ApiResponse<List<StudentResponse>>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData()).hasSize(5);
    }

    @Test
    @DisplayName("학생 정보를 조회한다.")
    void getStudent() throws Exception {
        HttpHeaders requestHeaders = getHttpHeaders();

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        long studentId = 1L;
        String url = getUrl() + "/" + studentId;

        ResponseEntity<ApiResponse<StudentResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData().getId()).isEqualTo(studentId);
        assertThat(result.getBody().getData().getName()).isEqualTo("임요환");
    }

    @Test
    @DisplayName("학생 정보를 등록한다.")
    void saveStudent() throws Exception {
        HttpHeaders requestHeaders = getHttpHeaders();

        StudentSaveRequest request = saveRequest();

        HttpEntity<StudentSaveRequest> requestHttpEntity = new HttpEntity<>(request, requestHeaders);

        String url = getUrl();

        ResponseEntity<ApiResponse<StudentResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getData().getName()).isEqualTo(request.getName());
    }

    @Test
    @DisplayName("학생 정보를 수정한다.")
    void updateStudent() throws Exception {
        HttpHeaders requestHeaders = getHttpHeaders();

        StudentUpdateRequest request = updateRequest();

        HttpEntity<StudentUpdateRequest> requestHttpEntity = new HttpEntity<>(request, requestHeaders);

        long studentId = 1L;
        String url = getUrl() + "/" + studentId;

        ResponseEntity<Void> result = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ApiResponse<StudentResponse>> finalResult = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(finalResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(finalResult.getBody().getData().getName()).isEqualTo(request.getName());
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
