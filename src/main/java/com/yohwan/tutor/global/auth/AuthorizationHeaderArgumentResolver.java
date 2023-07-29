package com.yohwan.tutor.global.auth;

import com.yohwan.tutor.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class AuthorizationHeaderArgumentResolver implements HandlerMethodArgumentResolver {
    private final TeacherRepository teacherRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticationTeacher.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(HttpHeaders.AUTHORIZATION);

        validateHeader(authorizationHeader);

        Long teacherId = validateNumberFormat(authorizationHeader);

        return teacherRepository.findById(teacherId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않은 아이디입니다.");
        });
    }

    private Long validateNumberFormat(String authorizationHeader) {
        Long teacherId;
        try {
            teacherId = Long.valueOf(authorizationHeader);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("인증 헤더 값은 숫자 형식이어야 합니다");
        }
        return teacherId;
    }

    private void validateHeader(String authorizationHeader) {
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new IllegalArgumentException("인증 정보는 필수입니다");
        }
    }
}
