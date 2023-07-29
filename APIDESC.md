# API 명세서

## GroupController
### 그룹 정보 조회

- **URL:** `/api/groups/{groupId}`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/groups/123
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **응답 예시:**
  ```json
  {
    "data": {
      "id": 123,
      "name": "그룹 이름",
      "standardDescription": "그룹 설명",
      "students": [
        {
          "id": 1,
          "name": "학생 1"
        },
        {
          "id": 2,
          "name": "학생 2"
        }
      ]
    },
    "message": null
  }
  ```

### 추천 문제 목록 조회

- **URL:** `/api/groups/{groupId}/recommend-questions`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/groups/123/recommend-questions
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`

- **응답 예시:**
  ```json
  {
    "data": [
      {
        "id": 1,
        "level": 2,
        "description": "문제 1"
      },
      {
        "id": 2,
        "level": 3,
        "description": "문제 2"
      }
    ],
    "message": null
  }
  ```
## QuestionController
### 문제 목록 조회

- **URL:** `/api/questions`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/questions
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **응답 예시:**
  ```json
  {
    "data": [
      {
        "id": 1,
        "level": 2,
        "description": "문제 1"
      },
      {
        "id": 2,
        "level": 3,
        "description": "문제 2"
      }
    ],
    "message": null
  }
  ```

### 문제 조회

- **URL:** `/api/questions/{questionId}`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/questions/123
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **응답 예시:**
  ```json
  {
    "data": {
      "id": 123,
      "level": 2,
      "description": "문제 내용"
    },
    "message": null
  }
  ```

### 문제 생성

- **URL:** `/api/questions`
- **Method:** POST
- **요청 예시:**
  ```json
  POST /api/questions
  {
    "level": 3,
    "description": "새로운 문제"
  }
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **요청 바디:**
    - `level`: 문제 난이도를 나타내는 정수
    - `description`: 문제 설명을 나타내는 문자열
- **응답 예시:**
    - 성공적으로 요청이 수행되면 상태 코드 201 Created를 반환합니다. 응답 헤더의 `Location`에 생성된 문제의 URL이 포함됩니다.

### 문제 수정

- **URL:** `/api/questions/{questionId}`
- **Method:** PUT
- **요청 예시:**
  ```json
  PUT /api/questions/123
  {
    "level": 4,
    "description": "수정된 문제"
  }
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **요청 바디:**
    - `level`: 수정할 문제의 새로운 난이도를 나타내는 정수
    - `description`: 수정할 문제의 새로운 설명을 나타내는 문자열
- **응답 예시:**
    - 성공적으로 요청이 수행되면 상태 코드 204 No Content를 반환합니다.


## StudentController
### 학생 목록 조회

- **URL:** `/api/students`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/students
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **응답 예시:**
  ```json
  {
    "data": [
      {
        "id": 1,
        "name": "학생 1"
      },
      {
        "id": 2,
        "name": "학생 2"
      }
    ],
    "message": null
  }
  ```

### 학생 조회

- **URL:** `/api/students/{studentId}`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/students/123
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **응답 예시:**
  ```json
  {
    "data": {
      "id": 123,
      "name": "학생 이름"
    },
    "message": null
  }
  ```

### 학생 생성

- **URL:** `/api/students`
- **Method:** POST
- **요청 예시:**
  ```json
  POST /api/students
  {
    "name": "새로운 학생"
  }
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **요청 바디:**
    - `name`: 학생 이름을 나타내는 문자열
- **응답 예시:**
    - 성공적으로 요청이 수행되면 상태 코드 201 Created를 반환합니다. 응답 헤더의 `Location`에 생성된 학생의 URL이 포함됩니다.

### 학생 수정

- **URL:** `/api/students/{studentId}`
- **Method:** PUT
- **요청 예시:**
  ```json
  PUT /api/students/123
  {
    "name": "수정된 학생"
  }
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **요청 바디:**
    - `name`: 수정할 학생의 새로운 이름을 나타내는 문자열
- **응답 예시:**
    - 성공적으로 요청이 수행되면 상태 코드 204 No Content를 반환합니다.


## TeacherController
### 내 정보 조회

- **URL:** `/api/teachers/me`
- **Method:** GET
- **요청 예시:**
  ```
  GET /api/teachers/me
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **응답 예시:**
  ```json
  {
    "data": {
      "id": 123,
      "name": "선생님 이름",
      "memo": "선생님 메모"
    },
    "message": null
  }
  ```

### 내 정보 수정

- **URL:** `/api/teachers/me`
- **Method:** PUT
- **요청 예시:**
  ```json
  PUT /api/teachers/me
  {
    "name": "새로운 이름",
    "memo": "새로운 메모"
  }
  ```
- **요청 헤더:**
    - `Authorization: teacherId(필수 값)`
- **요청 바디:**
    - `name`: 새로운 이름을 나타내는 문자열
    - `memo`: 새로운 메모를 나타내는 문자열
- **응답 예시:**
    - 성공적으로 요청이 수행되면 상태 코드 204 No Content를 반환합니다.