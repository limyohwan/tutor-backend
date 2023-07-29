package com.yohwan.tutor.integration;

import com.yohwan.tutor.annotation.IntegrationTest;
import com.yohwan.tutor.global.dto.ApiResponse;
import com.yohwan.tutor.data.TeacherTest;
import com.yohwan.tutor.dto.request.TeacherUpdateRequest;
import com.yohwan.tutor.dto.response.TeacherResponse;
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
public class TeacherControllerIntegrationTest extends TeacherTest {
    private static final String HOST_URL = "http://localhost:";
    private static final String BASE_URL = "/api/teachers";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("나의 정보(선생님)를 조회한다.")
    void getMyInfo() {
        HttpHeaders requestHeaders = getHttpHeaders();

        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(requestHeaders);

        String url = getUrl() + "/me";

        ResponseEntity<ApiResponse<TeacherResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getData().getName()).isEqualTo("임요환");
        assertThat(result.getBody().getData().getMemo()).isEqualTo("열혈선생님");
    }

    @Test
    @DisplayName("나의 정보(선생님)를 수정한다.")
    void updateMyInfo() {
        HttpHeaders requestHeaders = getHttpHeaders();

        TeacherUpdateRequest request = updateRequest();

        HttpEntity<TeacherUpdateRequest> requestHttpEntity = new HttpEntity<>(request, requestHeaders);

        String url = getUrl() + "/me";

        ResponseEntity<ApiResponse<TeacherResponse>> result = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ApiResponse<TeacherResponse>> finalResult = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(finalResult.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(finalResult.getBody().getData().getName()).isEqualTo(request.getName());
        assertThat(finalResult.getBody().getData().getMemo()).isEqualTo(request.getMemo());
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
