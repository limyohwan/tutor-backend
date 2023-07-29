# DB 명세서

## answer 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| answer_id            | BIGSERIAL   | NOT NULL, PK    |
| member_id            | BIGINT      |                |
| recommend_question_id| BIGINT      |                |
| student_id           | BIGINT      |                |
| question_id          | BIGINT      |                |
| is_correct           | BOOLEAN     |                |
| time                 | INTEGER     | NOT NULL        |

- `answer_id`: 답변 ID (BIGSERIAL, NOT NULL, PK)
- `member_id`: 멤버 ID (BIGINT)
- `recommend_question_id`: 추천 문제 ID (BIGINT)
- `student_id`: 학생 ID (BIGINT)
- `question_id`: 문제 ID (BIGINT)
- `is_correct`: 정답 여부 (BOOLEAN)
- `time`: 소요 시간 (INTEGER, NOT NULL)

## group 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| group_id             | BIGSERIAL   | NOT NULL, PK    |
| name                 | VARCHAR(255)|                |
| standard_description | VARCHAR(255)|                |

- `group_id`: 그룹 ID (BIGSERIAL, NOT NULL, PK)
- `name`: 그룹 이름 (VARCHAR(255))
- `standard_description`: 표준 설명 (VARCHAR(255))


## member 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| member_id            | BIGSERIAL   | NOT NULL, PK    |
| group_id             | BIGINT      |                |
| student_id           | BIGINT      |                |

- `member_id`: 멤버 ID (BIGSERIAL, NOT NULL, PK)
- `group_id`: 그룹 ID (BIGINT)
- `student_id`: 학생 ID (BIGINT)

**Unique 제약 조건:**

- 제약 조건 이름: `member_uk`
- 컬럼: `group_id`, `student_id`


## question 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| question_id          | BIGSERIAL   | NOT NULL, PK    |
| level                | INTEGER     |                |
| description          | VARCHAR(255)|                |

- `question_id`: 문제 ID (BIGSERIAL, NOT NULL, PK)
- `level`: 난이도 (INTEGER)
- `description`: 문제 내용 (VARCHAR(255))


## recommend_question 테이블

| 컬럼명                   | 데이터 타입 | 제약 조건       |
|--------------------------|-------------|-----------------|
| recommend_question_id    | BIGSERIAL   | NOT NULL, PK    |
| group_id                 | BIGINT      |                |
| question_id              | BIGINT      |                |

- `recommend_question_id`: 추천 문제 ID (BIGSERIAL, NOT NULL, PK)
- `group_id`: 그룹 ID (BIGINT)
- `question_id`: 문제 ID (BIGINT)

**Unique 제약 조건:**

- 제약 조건 이름: `recommend_question_uk`
- 컬럼: `group_id`, `question_id`


## teacher 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| teacher_id           | BIGSERIAL   | NOT NULL, PK    |
| name                 | VARCHAR(255)|                |
| memo                 | VARCHAR(255)|                |

- `teacher_id`: 선생님 ID (BIGSERIAL, NOT NULL, PK)
- `name`: 선생님 이름 (VARCHAR(255))
- `memo`: 선생님 메모 (VARCHAR(255))


## student 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| student_id           | BIGSERIAL   | NOT NULL, PK    |
| name                 | VARCHAR(255)|                |

- `student_id` : 학생 ID (BIGSERIAL, NOT NULL, PK)
- `name` : 학생 이름 (VARCHAR(255))


## student_teacher 테이블

| 컬럼명               | 데이터 타입 | 제약 조건       |
|----------------------|-------------|-----------------|
| student_id           | BIGINT      | PK, FK (Student)|
| teacher_id           | BIGINT      | PK, FK (Teacher)|

- `student_id` : 학생 ID (BIGINT, PK)
- `teacher_id` : 선생님 ID (BIGINT, PK)