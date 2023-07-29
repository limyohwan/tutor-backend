INSERT INTO teacher(teacher_id, name, memo)
VALUES(1, '임요환', '열혈선생님')
, (2, '하니', '뉴진스')
, (3, '해린', '뉴진스')
, (4, '민니', '아이들')
, (5, '민지', '뉴진스');
ALTER SEQUENCE teacher_teacher_id_seq RESTART WITH 6;

INSERT INTO student(student_id, name)
VALUES(1, '임요환')
, (2, '유리')
, (3, '티파니')
, (4, '윤아')
, (5, '태연');
ALTER SEQUENCE student_student_id_seq RESTART WITH 6;

INSERT INTO student_teacher(student_id, teacher_id)
VALUES(1, 1)
, (1, 2)
, (2, 2)
, (3, 3)
, (4, 4)
, (5, 5);

INSERT INTO groups(group_id, name, standard_description)
VALUES(1, '쉬운 그룹', '쉬운 문제들을 푸는 학생들 모임')
, (2, '중간 그룹', '중간 문제들을 푸는 학생들 모임')
, (3, '어려운 그룹', '어려운 문제들을 푸는 학생들 모임');
ALTER SEQUENCE groups_group_id_seq RESTART WITH 4;

INSERT INTO member(member_id, group_id, student_id)
VALUES(1, 1, 1)
, (2, 1, 2)
, (3, 1, 3)
, (4, 1, 4)
, (5, 1, 5)
, (6, 2, 1)
, (7, 2, 2)
, (8, 3, 1)
, (9, 3, 2)
, (10, 3, 3);
ALTER SEQUENCE member_member_id_seq RESTART WITH 11;

INSERT INTO question(question_id, level, description)
VALUES(1, 1, '쉬운 문제 1')
, (2, 1, '쉬운 문제 2')
, (3, 1, '쉬운 문제 3')
, (4, 2, '중간 문제 1')
, (5, 2, '중간 문제 2')
, (6, 2, '중간 문제 3')
, (7, 3, '어려운 문제 1')
, (8, 3, '어려운 문제 2')
, (9, 3, '어려운 문제 3');
ALTER SEQUENCE question_question_id_seq RESTART WITH 10;

INSERT INTO recommend_question(recommend_question_id, group_id, question_id)
VALUES(1, 1, 1)
, (2, 1, 2)
, (3, 1, 3)
, (4, 2, 4)
, (5, 2, 5)
, (6, 2, 6)
, (7, 3, 7)
, (8, 3, 8)
, (9, 3, 9);
ALTER SEQUENCE recommend_question_recommend_question_id_seq RESTART WITH 10;

INSERT INTO answer(answer_id, student_id, question_id, is_correct, time)
VALUES(1, 1, 1, true, 22)
, (2, 1, 5, true, 79)
, (3, 1, 8, false, 16)
, (4, 1, 9, false, 33)
, (5, 2, 1, true, 75)
, (6, 2, 4, true, 11)
, (7, 2, 4, true, 52)
, (8, 3, 1, true, 43)
, (9, 3, 4, false, 30)
, (10, 3, 8, true, 11)
, (11, 4, 1, true, 23)
, (12, 4, 2, true, 46)
, (13, 4, 3, true, 26)
, (14, 5, 1, true, 88)
, (15, 5, 2, true, 13);
ALTER SEQUENCE answer_answer_id_seq RESTART WITH 16;