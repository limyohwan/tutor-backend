# 구현 내용
1. API는 로그인된 선생님 식별자를 기준으로 동작
- AuthenticationTeacher 라는 커스텀 애노테이션과 AuthorizationHeaderArgumentResolver 클래스를 만들어 Http Header의 Authorization 타입으로 선생님의 ID를 전달받아 처리하도록 구현하였습니다.

2. 모델과 속성 구현
- Teacher 엔티티 = 선생님 정보
- Student 엔티티 = 학생 정보
- Group 엔티티 = 그룹 정보
- Question 엔티티 = 문제 정보
- Answer 엔티티 = 한생의 문제 풀이 로그 데이터 정보(답변)
- RecommendQuestion 엔티티 = 그룹에 추천하는 문제 정보
- StudentTeacher = 학생과 선생 사이의 관계를 나타내는 정보(@ManyToMany 매핑으로 구성하였습니다, @ManyToMany는 보통 실무에서 잘 사용할 일이 없지만 별도의 정보가 없을 것 같아 사용하였습니다.)

3. 선생님은 API를 통해 개인정보 메모를 수정, 조회 가능함
- GET : /api/teachers/me = 개인정보(이름으로 간략하게 구성), 메모를 조회합니다.
- PUT : /api/teachers/me = 개인정보(이름으로 간략하게 구성), 메모를 수정합니다.
- 자신의 정보는 @AuthenticationTeacher에 담겨있는 정보를 사용하였습니다.

4. 선생님은 API를 통해 학생정보를 입력하여 학생을 생성, 수정, 조회 가능함
- GET : /api/students = 학생들의 목록을 조회합니다.
- GET : /api/students/{studentId} = 학생의 아이디를 통해 특정 학생을 조회합니다.
- POST : /api/students = 학생정보(이름으로 간략하게 구성)를 입력하여 학생을 생성합니다.
- PUT : /api/students/{studentId} = 생정보(이름으로 간략하게 구성)를 입력하여 학생을 수정합니다.
- 자신이 담당하는 학생들만 조회해야되는 부분인지 정확하지 않아 모든 학생을 조회할 수 있도록 구성했습니다.

5. 선생님은 API를 통해 문제를 생성, 수정, 조회 가능함
- GET : /api/questions = 문제들의 목록을 조회합니다.
- GET : /api/questions/{questionId} = 문제의 아이디를 통해 특정 문제를 조회합니다.
- POST : /api/questions = 문제정보(난이도, 설명)를 입력하여 문제를 생성합니다.
- PUT : /api/questions/{questionId} = 문제정보(난이도, 설명)를 입력하여 문제를 수정합니다.

6. 선생님은 API를 통해 그룹생성이 완료된 이후 그룹정보를 조회 가능함
- GET : /api/groups/{groupId} = 그룹정보(이름, 학생목록, 기준)를 조회합니다.

7. 선생님을 API를 통해 그룹에 대해 추천하는 문제 목록을 조회 가능함
- GET : /api/groups/{groupId}/recommend-questions = 그룹에 대해 추천하는 문제 목록을 조회합니다.

# 패키지 구조
![패키지 구조.png](image%2F%ED%8C%A8%ED%82%A4%EC%A7%80%20%EA%B5%AC%EC%A1%B0.png)

1. controller = 컨트롤러 클래스들
2. domain = 엔티티 클래스들
3. dto = dto 클래스들
- request와 response로 구분
4. repository = 레포지토리 클래스들
5. service = 서비스 클래스들
6. global = 공통 클래스들
- auth = AuthenticationTeacher 애노테이션과 argumentResolver 위치
- config = spring 사용을 위한 성정파일과와 swagger를 위한 설정파일 위치
- dto = ApiResponse 공통 처리를 위한 dto
- exception = 공통 에러 처리를 위한 exceptionHandler

# 애플리케이션 실행
```
docker-compose up -d
```
1. 프로젝트 루트에서 docker-compose.yml 파일을 사용하여 위 명령어를 통해 PostgreSQL11을 실행합니다.
- 5432 포트의 tutor-local(application 전용) 데이터베이스와 5433 포트의 tutor-test(test 전용) 데이터베이스가 실행됩니다.

2. TutorApplication 클래스의 main을 실행하여 애플리케이션을 동작합니다.

3. 애플리케이션이 정상적으로 실행되면 http://localhost:8080/swagger-ui/index.html 에 접속하여 swagger를 통해 api를 간략하게 테스트해볼 수 있습니다.
- swagger의 데이터 폼 형식이 이상하니 API 명세서를 참고해주시면 될 것 같습니다.

4. 초기 데이터는 resources/db/migration/V2__init_data.sql 이 곳에 위치해 있으며 flyway를 사용하여 등록합니다.

# 테스트 데이터 소개
1. 5명의 선생님(1.임요환, 2.하니, 3.해린, 4.민니, 5.민지)
2. 5명의 학생(1.임요환, 2.유리, 3.티파니, 4.윤아, 5.태연)
3. 담당
- 1번 선생님 = 1, 2번 학생
- 2번 선생님 = 2번 학생
- 3번 선생님 = 3번 학생
- 4번 선생님 = 4번 학생
- 5번 선생님 = 5번 학생
4. 3개의 그룹
- 1.쉬운 그룹
- 2.중간 그룹
- 3.어려운 그룹
5. 그룹의 멤버
- 1번 그룹 = 1, 2, 3, 4, 5번 학생
- 2번 그룹 = 1, 2번 학생
- 3번 그룹 = 1, 2, 3번 학생
6. 9개의 문제
7. 추천 문제
- 1번 그룹 = 1, 2, 3번 문제(쉬운 문제)
- 2번 그룹 = 4, 5, 6번 문제(중간 문제)
- 3번 그룹 = 7, 8, 9번 문제(어려운 문제)
8. 문제 풀이 데이터는 임의로 정의하였습니다.

- 테스트 데이터는 테스트 코드의 통합 테스트에서도 똑같이 사용됩니다.

# 테스트
![테스트 구조.png](image%2F%ED%85%8C%EC%8A%A4%ED%8A%B8%20%EA%B5%AC%EC%A1%B0.png)

1. annotation
- ControllerTest = 컨트롤러 유닛 테스트(@WebMvcTest + Mockito)
- RepositoryTest = 레포지토리 유닛 테스트(@DataJpaTest)
- ServiceTest = 서비스 유닛 테스트(Mockito)
- IntegrationTest = 통합 테스트(@SpringBootTest + TestRestTemplate)
2. controller = 컨트롤러 유닛 테스트
3. repository = 레포지토리 유닛 테스트
4. serivce = 서비스 유닛 테스트
5. integration = 통합 테스트
6. data = 각 도메인별 테스트 데이터를 받을 수 있는 클래스가 위치한 패키지, 상속하여 사용